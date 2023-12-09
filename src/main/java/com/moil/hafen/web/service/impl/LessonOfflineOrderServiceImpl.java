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
import com.moil.hafen.web.dao.LessonOfflineOrderDao;
import com.moil.hafen.web.domain.*;
import com.moil.hafen.web.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class LessonOfflineOrderServiceImpl extends ServiceImpl<LessonOfflineOrderDao, LessonOfflineOrder> implements LessonOfflineOrderService {
    @Resource
    private LessonOfflineService lessonOfflineService;
    @Resource
    private CustomerService customerService;
    @Resource
    private CouponCustomerService couponCustomerService;
    @Resource
    private CouponService couponService;
    @Resource
    private StudentService studentService;
    @Resource
    private CampusService campusService;
    @Resource
    private LessonCampusPriceService lessonCampusPriceService;
    @Resource
    private LessonOfflineStudentRecordService lessonOfflineStudentRecordService;
    @Resource
    private WxPayService wxService;

    @Override
    public LessonOfflineOrder createOrder(LessonOfflineOrder order) {
        LessonOffline lessonOffline = lessonOfflineService.getById(order.getLessonId());
        LessonCampusPrice lessonCampusPrice = lessonCampusPriceService.getById(order.getLessonCampusPriceId());
        order.setLessonName(lessonOffline.getName());
        Double actualPrice = lessonCampusPrice.getActualPrice();
        double totalPrice = actualPrice*order.getCount();
        order.setPrice(totalPrice);
        order.setOrderSn(PayUtil.generateOrderNo("LFN"));
        order.setCreateTime(new Date());
        order.setModifyTime(new Date());
        int customerId = JWTUtil.getCurrentCustomerId();
        order.setCustomerId(customerId);
        Customer customer = customerService.getById(customerId);
        order.setNickName(customer.getNickName());
        order.setMobile(customer.getMobile());
        order.setPayMethod(0);
        order.setOrderSource(0);
        order.setOrderType(0);
        order.setType(lessonOffline.getType());
        Student student = studentService.getById(order.getStudentId());
        order.setStudentName(student.getStudentName());
        Campus campus = campusService.getById(order.getCampusId());
        order.setCampusName(campus.getName());


        //计算优惠
        Double actualPayment = totalPrice;
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
        LessonOfflineOrder lessonOfflineOrder = new LessonOfflineOrder();
        lessonOfflineOrder.setId(orderId);
        lessonOfflineOrder.setStatus(3);
        lessonOfflineOrder.setAfterSalesReason(afterSalesReason);
        lessonOfflineOrder.setAfterSalesTime(new Date());
        this.updateById(lessonOfflineOrder);
    }

    @Override
    public IPage<LessonOfflineOrder> getPage(QueryRequest request, LessonOfflineOrder lessonOfflineOrder) {
        Page<LessonOfflineOrder> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        return this.page(page, getCondition(lessonOfflineOrder));
    }

    @Override
    public void refund(Integer id) throws WxPayException {
        LessonOfflineOrder lessonOfflineOrder = this.getById(id);
        refund(lessonOfflineOrder);
        lessonOfflineOrder.setStatus(4);
        lessonOfflineOrder.setOrderType(1);
        this.updateById(lessonOfflineOrder);
        int count = lessonOfflineOrder.getUseLessonCount()-(lessonOfflineOrder.getLessonCount()+lessonOfflineOrder.getGiveLessonCount());
        LessonOfflineStudentRecord lessonOfflineStudentRecord = new LessonOfflineStudentRecord(lessonOfflineOrder.getLessonId(), lessonOfflineOrder.getCustomerId(),
                lessonOfflineOrder.getStudentId(), count, "退费", new Date());
        lessonOfflineStudentRecordService.save(lessonOfflineStudentRecord);
    }
    private void refund(LessonOfflineOrder lessonOfflineOrder) throws WxPayException {
        WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
        wxPayRefundRequest.setOutRefundNo(PayUtil.generateOrderNo("RF_LFN"));
        wxPayRefundRequest.setTotalFee((int)(lessonOfflineOrder.getActualPayment()*100));
        wxPayRefundRequest.setRefundFee((int)(lessonOfflineOrder.getActualPayment()*100));
        wxPayRefundRequest.setOutTradeNo(lessonOfflineOrder.getOrderSn());
        wxService.refund(wxPayRefundRequest);
    }

    @Override
    public List<LessonOfflineOrder> getlessonOfflineOrderList(LessonOfflineOrder lessonOfflineOrder) {
        return this.list(getCondition(lessonOfflineOrder));
    }
    private LambdaQueryWrapper<LessonOfflineOrder> getCondition(LessonOfflineOrder lessonOfflineOrder){
        LambdaQueryWrapper<LessonOfflineOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(lessonOfflineOrder.getOrderSn())){
            lambdaQueryWrapper.eq(LessonOfflineOrder::getOrderSn, lessonOfflineOrder.getOrderSn());
        }
        if(lessonOfflineOrder.getStatus() != null){
            lambdaQueryWrapper.eq(LessonOfflineOrder::getStatus, lessonOfflineOrder.getStatus());
        }
        if(StringUtils.isNotBlank(lessonOfflineOrder.getCreateTimeFrom())){
            lambdaQueryWrapper.ge(LessonOfflineOrder::getCreateTime,lessonOfflineOrder.getCreateTimeFrom()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(lessonOfflineOrder.getCreateTimeTo())){
            lambdaQueryWrapper.le(LessonOfflineOrder::getCreateTime,lessonOfflineOrder.getCreateTimeTo()+" 23:59:59");
        }
        if(StringUtils.isNotBlank(lessonOfflineOrder.getNickName())){
            lambdaQueryWrapper.eq(LessonOfflineOrder::getNickName, lessonOfflineOrder.getNickName());
        }
        if(StringUtils.isNotBlank(lessonOfflineOrder.getMobile())){
            lambdaQueryWrapper.eq(LessonOfflineOrder::getMobile, lessonOfflineOrder.getMobile());
        }
        if(lessonOfflineOrder.getCampusId() != null){
            lambdaQueryWrapper.eq(LessonOfflineOrder::getCampusId, lessonOfflineOrder.getCampusId());
        }
        if(lessonOfflineOrder.getPayMethod() != null){
            lambdaQueryWrapper.eq(LessonOfflineOrder::getPayMethod, lessonOfflineOrder.getPayMethod());
        }
        if(lessonOfflineOrder.getOrderType() != null){
            lambdaQueryWrapper.eq(LessonOfflineOrder::getOrderType, lessonOfflineOrder.getOrderType());
        }

        return lambdaQueryWrapper;
    }
}
