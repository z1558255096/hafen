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
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.PayUtil;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.LessonOnlineOrderDao;
import com.moil.hafen.web.domain.*;
import com.moil.hafen.web.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class LessonOnlineOrderServiceImpl extends ServiceImpl<LessonOnlineOrderDao, LessonOnlineOrder> implements LessonOnlineOrderService {
    @Resource
    private LessonOnlineService lessonOnlineService;
    @Resource
    private CustomerService customerService;
    @Resource
    private CouponCustomerService couponCustomerService;
    @Resource
    private CouponService couponService;
    @Resource
    private WxPayService wxService;

    @Override
    public LessonOnlineOrder createOrder(LessonOnlineOrder order) {
        LessonOnline lessonOnline = lessonOnlineService.getById(order.getLessonId());

        order.setLessonName(lessonOnline.getName());
        order.setPrice(lessonOnline.getActualPrice());
        order.setOrderSn(PayUtil.generateOrderNo("LON"));
        order.setCreateTime(new Date());
        order.setModifyTime(new Date());
        int customerId = JWTUtil.getCurrentCustomerId();
        order.setCustomerId(customerId);
        Customer customer = customerService.getById(customerId);
        order.setNickName(customer.getNickName());
        order.setMobile(customer.getMobile());
        order.setPayMethod(0);
        order.setOrderSource(0);
        order.setType(lessonOnline.getType());


        //计算优惠
        Double actualPayment = lessonOnline.getActualPrice();
        Integer couponId = order.getCouponId();
        if(couponId != null && couponId>0){
            CouponCustomer couponCustomer = couponCustomerService.getById(couponId);
            if(couponCustomer.getStatus()==0){
                Coupon coupon = couponService.getById(couponCustomer.getCouponId());
                actualPayment = actualPayment - coupon.getDiscountPrice();
            }
        }
        Integer hafenCoin = order.getHafenCoin();
        if(hafenCoin!=null&&hafenCoin>0){
            actualPayment = actualPayment-hafenCoin;
        }
        if(actualPayment < 0 ){
            actualPayment = 0.0;
        }
        order.setActualPayment(actualPayment);
        order.setStatus(0);
        this.save(order);
        if(couponId != null && couponId>0){
            couponCustomerService.update(new LambdaUpdateWrapper<CouponCustomer>().set(CouponCustomer::getStatus,1).set(CouponCustomer::getUseTime,new Date())
                    .eq(CouponCustomer::getCustomerId,couponId));
        }
        return order;
    }

    @Override
    public void afterSales(Integer orderId, String afterSalesReason) {
        LessonOnlineOrder lessonOnlineOrder = new LessonOnlineOrder();
        lessonOnlineOrder.setId(orderId);
        lessonOnlineOrder.setStatus(3);
        lessonOnlineOrder.setAfterSalesReason(afterSalesReason);
        lessonOnlineOrder.setAfterSalesTime(new Date());
        this.updateById(lessonOnlineOrder);
    }

    @Override
    public IPage<LessonOnlineOrder> getPage(QueryRequest request, LessonOnlineOrder lessonOnlineOrder) {
        Page<LessonOnlineOrder> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        return this.page(page, getCondition(lessonOnlineOrder));
    }

    @Override
    public void refund(Integer id) throws WxPayException {
        LessonOnlineOrder lessonOnlineOrder = this.getById(id);
        refund(lessonOnlineOrder);
        lessonOnlineOrder.setStatus(4);
        this.updateById(lessonOnlineOrder);
    }
    private void refund(LessonOnlineOrder lessonOnlineOrder) throws WxPayException {
        WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
        wxPayRefundRequest.setOutRefundNo(PayUtil.generateOrderNo("RF_LON"));
        wxPayRefundRequest.setTotalFee((int)(lessonOnlineOrder.getActualPayment()*100));
        wxPayRefundRequest.setRefundFee((int)(lessonOnlineOrder.getActualPayment()*100));
        wxPayRefundRequest.setOutTradeNo(lessonOnlineOrder.getOrderSn());
        wxService.refund(wxPayRefundRequest);
    }
    @Override
    public List<LessonOnlineOrder> getlessonOnlineOrderList(LessonOnlineOrder lessonOnlineOrder) {
        return this.list(getCondition(lessonOnlineOrder));
    }
    private LambdaQueryWrapper<LessonOnlineOrder> getCondition(LessonOnlineOrder lessonOnlineOrder){
        LambdaQueryWrapper<LessonOnlineOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(lessonOnlineOrder.getOrderSn())){
            lambdaQueryWrapper.eq(LessonOnlineOrder::getOrderSn, lessonOnlineOrder.getOrderSn());
        }
        if(lessonOnlineOrder.getStatus() != null){
            lambdaQueryWrapper.eq(LessonOnlineOrder::getStatus, lessonOnlineOrder.getStatus());
        }
        if(StringUtils.isNotBlank(lessonOnlineOrder.getCreateTimeFrom())){
            lambdaQueryWrapper.ge(LessonOnlineOrder::getCreateTime,lessonOnlineOrder.getCreateTimeFrom()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(lessonOnlineOrder.getCreateTimeTo())){
            lambdaQueryWrapper.le(LessonOnlineOrder::getCreateTime,lessonOnlineOrder.getCreateTimeTo()+" 23:59:59");
        }
        if(StringUtils.isNotBlank(lessonOnlineOrder.getNickName())){
            lambdaQueryWrapper.eq(LessonOnlineOrder::getNickName, lessonOnlineOrder.getNickName());
        }

        return lambdaQueryWrapper;
    }
}
