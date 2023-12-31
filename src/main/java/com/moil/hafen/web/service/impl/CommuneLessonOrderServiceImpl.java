package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.moil.hafen.common.constant.RedisKeyConstant;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.queue.RedisDelayedQueueListener;
import com.moil.hafen.common.queue.RedissonDelayQueue;
import com.moil.hafen.common.utils.Arith;
import com.moil.hafen.common.utils.LogUtil;
import com.moil.hafen.common.utils.PayUtil;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.common.utils.lock.LockManager;
import com.moil.hafen.web.dao.CommuneLessonOrderDao;
import com.moil.hafen.web.domain.*;
import com.moil.hafen.web.service.CommuneLessonOrderService;
import com.moil.hafen.web.service.CommuneLessonService;
import com.moil.hafen.web.service.CouponCustomerService;
import com.moil.hafen.web.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CommuneLessonOrderServiceImpl extends ServiceImpl<CommuneLessonOrderDao, CommuneLessonOrder> implements CommuneLessonOrderService, RedisDelayedQueueListener<CommuneLessonOrder> {
    @Resource
    private CommuneLessonService communeLessonService;
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
    public CommuneLessonOrder createOrder(CommuneLessonOrder order) {
        String orderSn = lockManager.execute
                (RedisKeyConstant.COMMUNE_LESSON_ORDER(String.valueOf(order.getCustomerId())),
                        () -> PayUtil.generateOrderNo("CLN"));
        CommuneLesson communeLesson = communeLessonService.getById(order.getLessonId());
        order.setLessonName(communeLesson.getName());
        Double actualPrice = communeLesson.getPrice();
        Double totalPrice = Arith.mul(actualPrice, order.getCount());
        order.setPrice(totalPrice);
        order.setOrderSn(orderSn);
        order.setCreateTime(new Date());
        order.setModifyTime(new Date());

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
        redissonDelayQueue.offerTask(order, CommuneLessonOrderServiceImpl.class);
        return order;
    }

    @Override
    public void afterSales(Integer orderId, String afterSalesReason) {
        CommuneLessonOrder communeLessonOrder = new CommuneLessonOrder();
        communeLessonOrder.setId(orderId);
        communeLessonOrder.setStatus(4);
        communeLessonOrder.setAfterSalesReason(afterSalesReason);
        communeLessonOrder.setAfterSalesTime(new Date());
        this.updateById(communeLessonOrder);
    }

    @Override
    public IPage<CommuneLessonOrder> getPage(QueryRequest request, CommuneLessonOrder communeLessonOrder) {
        Page<CommuneLessonOrder> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        return this.page(page, getCondition(communeLessonOrder));
    }

    @Override
    public void refund(Integer id) throws WxPayException {
        CommuneLessonOrder communeLessonOrder = this.getById(id);
        refund(communeLessonOrder);
        communeLessonOrder.setStatus(5);
        this.updateById(communeLessonOrder);
    }

    private void refund(CommuneLessonOrder communeLessonOrder) throws WxPayException {
        WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
        wxPayRefundRequest.setOutRefundNo(PayUtil.generateOrderNo("RF_CLN"));
        wxPayRefundRequest.setTotalFee((int) (communeLessonOrder.getActualPayment() * 100));
        wxPayRefundRequest.setRefundFee((int) (communeLessonOrder.getActualPayment() * 100));
        wxPayRefundRequest.setOutTradeNo(communeLessonOrder.getOrderSn());
        wxService.refund(wxPayRefundRequest);
    }

    @Override
    public List<CommuneLessonOrder> getCommuneLessonOrderList(CommuneLessonOrder communeLessonOrder) {
        return this.list(getCondition(communeLessonOrder));
    }

    @Override
    public void use(Integer id) {
        CommuneLessonOrder communeLessonOrder = this.getById(id);
        communeLessonOrder.setStatus(2);
        this.updateById(communeLessonOrder);
    }

    private LambdaQueryWrapper<CommuneLessonOrder> getCondition(CommuneLessonOrder communeLessonOrder) {
        LambdaQueryWrapper<CommuneLessonOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(communeLessonOrder.getOrderSn())) {
            lambdaQueryWrapper.eq(CommuneLessonOrder::getOrderSn, communeLessonOrder.getOrderSn());
        }
        if (communeLessonOrder.getStatus() != null) {
            lambdaQueryWrapper.eq(CommuneLessonOrder::getStatus, communeLessonOrder.getStatus());
        }
        if (StringUtils.isNotBlank(communeLessonOrder.getCreateTimeFrom())) {
            lambdaQueryWrapper.ge(CommuneLessonOrder::getCreateTime, communeLessonOrder.getCreateTimeFrom() + " 00:00:00");
        }
        if (StringUtils.isNotBlank(communeLessonOrder.getCreateTimeTo())) {
            lambdaQueryWrapper.le(CommuneLessonOrder::getCreateTime, communeLessonOrder.getCreateTimeTo() + " 23:59:59");
        }
        if (StringUtils.isNotBlank(communeLessonOrder.getNickName())) {
            lambdaQueryWrapper.eq(CommuneLessonOrder::getNickName, communeLessonOrder.getNickName());
        }
        return lambdaQueryWrapper;
    }

    @Override
    public void invoke(CommuneLessonOrder order) {
        CommuneLessonOrder byId = this.getById(order.getId());
        if(byId.getStatus() == 0) {
            LogUtil.info(log, "订单超时未支付，取消订单，订单号：{}", order.getOrderSn());
            byId.setStatus(3);//取消订单
            this.updateById(byId);
            //恢复优惠券使用状态
            Integer couponId = byId.getCouponId();
            couponCustomerService.update(new LambdaUpdateWrapper<CouponCustomer>().set(CouponCustomer::getStatus, 0).set(CouponCustomer::getUseTime, null)
                    .eq(CouponCustomer::getCustomerId, couponId));
        }
    }
}
