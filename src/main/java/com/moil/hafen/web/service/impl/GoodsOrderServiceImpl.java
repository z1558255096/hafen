package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.google.gson.Gson;
import com.kuaidi100.sdk.api.Subscribe;
import com.kuaidi100.sdk.contant.ApiInfoConstant;
import com.kuaidi100.sdk.core.IBaseClient;
import com.kuaidi100.sdk.request.SubscribeParam;
import com.kuaidi100.sdk.request.SubscribeParameters;
import com.kuaidi100.sdk.request.SubscribeReq;
import com.kuaidi100.sdk.utils.PropertiesReader;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.enums.AfterSalesStatus;
import com.moil.hafen.common.enums.AfterSalesType;
import com.moil.hafen.common.enums.GoodsOrderStatus;
import com.moil.hafen.common.utils.PayUtil;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.GoodsOrderDao;
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
public class GoodsOrderServiceImpl extends ServiceImpl<GoodsOrderDao, GoodsOrder> implements GoodsOrderService {
    @Resource
    private GoodsOrderLogisticsService goodsOrderLogisticsService;
    @Resource
    private GoodsOrderLogService goodsOrderLogService;
    @Resource
    private WxPayService wxService;
    @Resource
    private CustomerAddressService customerAddressService;
    @Resource
    private CouponService couponService;
    @Resource
    private CouponCustomerService couponCustomerService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private GoodsSpecsService goodsSpecsService;

    @Override
    public IPage<GoodsOrder> getPage(QueryRequest request, GoodsOrder goodsOrder) {
        Page<GoodsOrder> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        return this.page(page, getCondition(goodsOrder));
    }

    @Override
    public List<GoodsOrder> getGoodsOrderList(GoodsOrder goodsOrder) {
        return this.list(getCondition(goodsOrder));
    }

    @Override
    public void delivery(Integer id, String logisticsSn, String deliveryCode) throws Exception {
        GoodsOrder order = this.getById(id);
        subscribeLogistics(logisticsSn, deliveryCode, order.getConsigneeMobile());

        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setId(id);
        goodsOrder.setModifyTime(new Date());
        goodsOrder.setDeliveryCode(deliveryCode);
        goodsOrder.setLogisticsSn(logisticsSn);
        goodsOrder.setOrderStatus(GoodsOrderStatus.已发货待收货.state);
        this.updateById(goodsOrder);
    }

    @Override
    public GoodsOrder detail(Integer id) {
        GoodsOrder goodsOrder = this.getById(id);
        goodsOrder.setGoodsOrderLogs(goodsOrderLogService.list(new LambdaQueryWrapper<GoodsOrderLog>().eq(GoodsOrderLog::getOrderId,id).orderByDesc(GoodsOrderLog::getCreateTime)));
        goodsOrder.setGoodsOrderLogistics(goodsOrderLogisticsService.list(new LambdaQueryWrapper<GoodsOrderLogistics>().eq(GoodsOrderLogistics::getOrderId,id).orderByDesc(GoodsOrderLogistics::getCreateTime)));
        return goodsOrder;
    }

    @Override
    public void afterSalesApproval(Integer id, String status, String reason) throws WxPayException {
        GoodsOrder goodsOrder = this.getById(id);
        goodsOrder.setApprovalReason(reason);
        if(status.equals("拒绝")){
            goodsOrder.setAfterSalesStatus(AfterSalesStatus.拒绝.state);
            this.updateById(goodsOrder);
            return;
        }
        if(goodsOrder.getAfterSalesType()== AfterSalesType.仅退款.type){
            refund(goodsOrder);
            goodsOrder.setAfterSalesStatus(AfterSalesStatus.已退款.state);
        }else {
            goodsOrder.setAfterSalesStatus(AfterSalesStatus.待寄出.state);
        }
        this.updateById(goodsOrder);
    }

    @Override
    public void refund(Integer id) throws WxPayException {
        GoodsOrder goodsOrder = this.getById(id);
        refund(goodsOrder);
        goodsOrder.setAfterSalesStatus(AfterSalesStatus.已退款.state);
        this.updateById(goodsOrder);
    }

    @Override
    public GoodsOrder createOrder(GoodsOrder order) {
        GoodsSpecs goodsSpecs = goodsSpecsService.getById(order.getSpecsId());
        Goods goods = goodsService.getById(goodsSpecs.getGoodsId());
        order.setGoodsName(goods.getName());
        order.setImg(goods.getImg());
        order.setSpecs(goodsSpecs.getSpecs());
        double totalPrice = goodsSpecs.getPrice()*order.getCount();
        order.setTotalPrice(totalPrice);
        String orderNo = PayUtil.generateOrderNo("GBN");
        order.setOrderSn(orderNo);
        order.setCreateTime(new Date());
        order.setModifyTime(new Date());
        CustomerAddress customerAddress = customerAddressService.getById(order.getAddressId());
        String address = customerAddress.getProvince()+customerAddress.getCity()+customerAddress.getDistrict()+customerAddress.getAddress();
        order.setAddress(address);
        order.setConsignee(customerAddress.getConsignee());
        order.setConsigneeMobile(customerAddress.getMobile());
        order.setCustomerId(JWTUtil.getCurrentCustomerId());
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
        Double hafenCoin = order.getHafenCoin();
        if(hafenCoin!=null&&hafenCoin>0){
            actualPayment = actualPayment-hafenCoin;
        }
        if(actualPayment < 0 ){
            actualPayment = 0.0;
        }
        order.setActualPayment(actualPayment);
        order.setOrderStatus(GoodsOrderStatus.待支付.state);
        this.save(order);
        if(couponId != null && couponId>0){
            couponCustomerService.update(new LambdaUpdateWrapper<CouponCustomer>().set(CouponCustomer::getStatus,1).set(CouponCustomer::getUseTime,new Date())
                    .eq(CouponCustomer::getCustomerId,couponId));
        }
        return order;
    }

    @Override
    public void afterSales(Integer orderId, int afterSalesType, String afterSalesReason, String afterSalesCertificate) {
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setId(orderId);
        goodsOrder.setAfterSalesType(afterSalesType);
        goodsOrder.setAfterSalesReason(afterSalesReason);
        goodsOrder.setAfterSalesTime(new Date());
        goodsOrder.setAfterSalesStatus(AfterSalesStatus.待审核.state);
        goodsOrder.setAfterSalesCertificate(afterSalesCertificate);
        this.updateById(goodsOrder);
    }

    @Override
    public void updateAfterSalesLogisticsSn(Integer orderId, String logisticsSn) {
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setId(orderId);
        goodsOrder.setAfterSalesLogisticsSn(logisticsSn);
        this.updateById(goodsOrder);
    }

    @Override
    public void confirmReceipt(Integer orderId) {
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setId(orderId);
        goodsOrder.setOrderStatus(GoodsOrderStatus.已完成.state);
        this.updateById(goodsOrder);
    }

    @Override
    public List<GoodsOrderLogistics> getGoodsOrderLogistics(Integer orderId) {
        return goodsOrderLogisticsService.list(new LambdaQueryWrapper<GoodsOrderLogistics>().eq(GoodsOrderLogistics::getOrderId,orderId)
                .orderByDesc(GoodsOrderLogistics::getCreateTime));
    }

    private void refund(GoodsOrder goodsOrder) throws WxPayException {
        WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
        wxPayRefundRequest.setOutRefundNo(PayUtil.generateOrderNo("RF_GBN"));
        wxPayRefundRequest.setTotalFee((int)(goodsOrder.getActualPayment()*100));
        wxPayRefundRequest.setRefundFee((int)(goodsOrder.getActualPayment()*100));
        wxPayRefundRequest.setOutTradeNo(goodsOrder.getOrderSn());
        wxService.refund(wxPayRefundRequest);
    }

    String key = PropertiesReader.get("key");
    private void subscribeLogistics(String number, String company, String mobile) throws Exception{
        SubscribeParameters subscribeParameters = new SubscribeParameters();
        subscribeParameters.setCallbackurl("http://www.baidu.com");
        subscribeParameters.setPhone(mobile);

        SubscribeParam subscribeParam = new SubscribeParam();
        subscribeParam.setParameters(subscribeParameters);
//        subscribeParam.setCompany(CompanyConstant.ST);
        subscribeParam.setCompany(company);
        subscribeParam.setNumber(number);
        subscribeParam.setKey(key);

        SubscribeReq subscribeReq = new SubscribeReq();
        subscribeReq.setSchema(ApiInfoConstant.SUBSCRIBE_SCHEMA);
        subscribeReq.setParam(new Gson().toJson(subscribeParam));

        IBaseClient subscribe = new Subscribe();
        log.info("subscribeReq:{}",subscribe.execute(subscribeReq));
    }

    private LambdaQueryWrapper<GoodsOrder> getCondition(GoodsOrder goodsOrder){
        LambdaQueryWrapper<GoodsOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(goodsOrder.getOrderSn())){
            lambdaQueryWrapper.eq(GoodsOrder::getOrderSn, goodsOrder.getOrderSn());
        }
        if(goodsOrder.getOrderStatus() != null){
            lambdaQueryWrapper.eq(GoodsOrder::getOrderStatus, goodsOrder.getOrderStatus());
        }
        if(goodsOrder.getOrderStatus() != null){
            lambdaQueryWrapper.eq(GoodsOrder::getOrderStatus, goodsOrder.getOrderStatus());
        }
        if(StringUtils.isNotBlank(goodsOrder.getCreateTimeFrom())){
            lambdaQueryWrapper.ge(GoodsOrder::getCreateTime,goodsOrder.getCreateTimeFrom()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(goodsOrder.getCreateTimeTo())){
            lambdaQueryWrapper.le(GoodsOrder::getCreateTime,goodsOrder.getCreateTimeTo()+" 23:59:59");
        }
        if(StringUtils.isNotBlank(goodsOrder.getNickName())){
            lambdaQueryWrapper.eq(GoodsOrder::getNickName, goodsOrder.getNickName());
        }
        if(StringUtils.isNotBlank(goodsOrder.getGoodsName())){
            lambdaQueryWrapper.eq(GoodsOrder::getGoodsName, goodsOrder.getGoodsName());
        }

        return lambdaQueryWrapper;
    }
}
