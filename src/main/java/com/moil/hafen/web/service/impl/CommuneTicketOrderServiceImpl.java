package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.constant.RedisKeyConstant;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.queue.RedisDelayedQueueListener;
import com.moil.hafen.common.queue.RedissonDelayQueue;
import com.moil.hafen.common.utils.Arith;
import com.moil.hafen.common.utils.LogUtil;
import com.moil.hafen.common.utils.PayUtil;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.common.utils.lock.LockManager;
import com.moil.hafen.web.dao.CommuneTicketOrderDao;
import com.moil.hafen.web.domain.*;
import com.moil.hafen.web.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CommuneTicketOrderServiceImpl extends ServiceImpl<CommuneTicketOrderDao, CommuneTicketOrder> implements CommuneTicketOrderService, RedisDelayedQueueListener<CommuneTicketOrder> {
    @Resource
    private CommuneTicketService communeTicketService;
    @Resource
    private CustomerService customerService;
    @Resource
    private CouponCustomerService couponCustomerService;
    @Resource
    private CouponService couponService;
    @Resource
    private WxPayService wxService;
    @Resource
    private LockManager lockManager;
    @Resource
    private RedissonDelayQueue redissonDelayQueue;

    @Override
    public CommuneTicketOrder createOrder(CommuneTicketOrder order) {
        String orderSn = lockManager.execute
                (RedisKeyConstant.COMMUNE_TICKET_ORDER(String.valueOf(order.getCustomerId())),
                        () -> PayUtil.generateOrderNo("CLN"));
        CommuneTicket communeTicket = communeTicketService.getById(order.getTicketId());
        order.setTicketName(communeTicket.getName());
        Double actualPrice = communeTicket.getPrice();
        double totalPrice = Arith.mul(actualPrice, order.getCount());
        order.setPrice(totalPrice);
        order.setOrderSn(orderSn);
        order.setCreateTime(new Date());
        order.setModifyTime(new Date());
        int customerId = JWTUtil.getCurrentCustomerId();
        order.setCustomerId(customerId);
        Customer customer = customerService.getById(customerId);
        order.setNickName(customer.getNickName());
        order.setMobile(customer.getMobile());

        //计算优惠
        double actualPayment = totalPrice;
        Integer couponId = order.getCouponId();
        if (couponId != null && couponId > 0) {
            CouponCustomer couponCustomer = couponCustomerService.getById(couponId);
            if (couponCustomer.getStatus() == 0) {
                Coupon coupon = couponService.getById(couponCustomer.getCouponId());
                actualPayment = Arith.sub(actualPayment, coupon.getDiscountPrice());
            }
        }
        Integer hafenCoin = order.getHafenCoin();
        if (hafenCoin != null && hafenCoin > 0) {
            actualPayment = Arith.sub(actualPayment, hafenCoin);
        }
        if (actualPayment < 0) {
            actualPayment = 0.0;
        }
        order.setActualPayment(actualPayment);
        order.setStatus(0);
        this.save(order);
        if (couponId != null && couponId > 0) {
            couponCustomerService.update(new LambdaUpdateWrapper<CouponCustomer>().set(CouponCustomer::getStatus, 1).set(CouponCustomer::getUseTime, new Date())
                    .eq(CouponCustomer::getCustomerId, couponId));
        }
        // redisson延迟
        redissonDelayQueue.offerTask(order, CommuneTicketOrderServiceImpl.class);
        return order;
    }

    @Override
    public void afterSales(Integer orderId, String afterSalesReason) {
        CommuneTicketOrder communeTicketOrder = new CommuneTicketOrder();
        communeTicketOrder.setId(orderId);
        communeTicketOrder.setStatus(4);
        communeTicketOrder.setAfterSalesReason(afterSalesReason);
        communeTicketOrder.setAfterSalesTime(new Date());
        this.updateById(communeTicketOrder);
    }

    @Override
    public IPage<CommuneTicketOrder> getPage(QueryRequest request, CommuneTicketOrder communeTicketOrder) {
        Page<CommuneTicketOrder> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        return this.page(page, getCondition(communeTicketOrder));
    }

    @Override
    public void refund(Integer id) throws WxPayException {
        CommuneTicketOrder communeTicketOrder = this.getById(id);
        refund(communeTicketOrder);
        communeTicketOrder.setStatus(5);
        this.updateById(communeTicketOrder);
    }

    private void refund(CommuneTicketOrder communeTicketOrder) throws WxPayException {
        WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
        wxPayRefundRequest.setOutRefundNo(PayUtil.generateOrderNo("RF_CTN"));
        wxPayRefundRequest.setTotalFee((int) (communeTicketOrder.getActualPayment() * 100));
        wxPayRefundRequest.setRefundFee((int) (communeTicketOrder.getActualPayment() * 100));
        wxPayRefundRequest.setOutTradeNo(communeTicketOrder.getOrderSn());
        wxService.refund(wxPayRefundRequest);
    }

    @Override
    public List<CommuneTicketOrder> getCommuneTicketOrderList(CommuneTicketOrder communeTicketOrder) {
        return this.list(getCondition(communeTicketOrder));
    }

    @Override
    public void use(Integer id) {
        CommuneTicketOrder communeTicketOrder = this.getById(id);
        communeTicketOrder.setStatus(2);
        this.updateById(communeTicketOrder);
    }

    private LambdaQueryWrapper<CommuneTicketOrder> getCondition(CommuneTicketOrder communeTicketOrder) {
        LambdaQueryWrapper<CommuneTicketOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(communeTicketOrder.getOrderSn())) {
            lambdaQueryWrapper.eq(CommuneTicketOrder::getOrderSn, communeTicketOrder.getOrderSn());
        }
        if (communeTicketOrder.getStatus() != null) {
            lambdaQueryWrapper.eq(CommuneTicketOrder::getStatus, communeTicketOrder.getStatus());
        }
        if (StringUtils.isNotBlank(communeTicketOrder.getCreateTimeFrom())) {
            lambdaQueryWrapper.ge(CommuneTicketOrder::getCreateTime, communeTicketOrder.getCreateTimeFrom() + " 00:00:00");
        }
        if (StringUtils.isNotBlank(communeTicketOrder.getCreateTimeTo())) {
            lambdaQueryWrapper.le(CommuneTicketOrder::getCreateTime, communeTicketOrder.getCreateTimeTo() + " 23:59:59");
        }
        if (StringUtils.isNotBlank(communeTicketOrder.getNickName())) {
            lambdaQueryWrapper.eq(CommuneTicketOrder::getNickName, communeTicketOrder.getNickName());
        }
        return lambdaQueryWrapper;
    }

    @Override
    public void invoke(CommuneTicketOrder communeTicketOrder) {
        CommuneTicketOrder byId = this.getById(communeTicketOrder.getId());
        if(byId.getStatus() == 0){
            LogUtil.info(log, "订单超时未支付，取消订单，订单号：{}", communeTicketOrder.getOrderSn());
            byId.setStatus(3);//取消订单
            this.updateById(byId);
            //恢复优惠券使用状态
            Integer couponId = byId.getCouponId();
            couponCustomerService.update(new LambdaUpdateWrapper<CouponCustomer>().set(CouponCustomer::getStatus, 0).set(CouponCustomer::getUseTime, null)
                    .eq(CouponCustomer::getCustomerId, couponId));
        }
    }
}
