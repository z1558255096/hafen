package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.moil.hafen.common.enums.GoodsOrderStatus;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.*;
import com.moil.hafen.web.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private CustomerService customerService;
    @Resource
    private GoodsOrderService goodsOrderService;
    @Resource
    private LessonOnlineOrderService lessonOnlineOrderService;
    @Resource
    private LessonOfflineOrderService lessonOfflineOrderService;
    @Resource
    private CommuneLessonOrderService communeLessonOrderService;
    @Resource
    private CommuneTicketOrderService communeTicketOrderService;
    @Resource
    private LessonOfflineStudentRecordService lessonOfflineStudentRecordService;
    @Override
    public WxPayUnifiedOrderRequest createWxOrder(String orderSn) throws FebsException {
        String body = null;
        int fee = 0;
        int customerId = 0;
        if(orderSn.startsWith("GBN")){
            GoodsOrder orderGoods = goodsOrderService.getOne(new LambdaQueryWrapper<GoodsOrder>().eq(GoodsOrder::getOrderSn,orderSn));

            if(orderGoods == null){
                throw new FebsException("订单号不存在");
            }
            double payAmount = orderGoods.getActualPayment();
            fee = (int)(payAmount*100);
            body = orderGoods.getGoodsName();
            customerId = orderGoods.getCustomerId();

        } else if (orderSn.startsWith("LON")) {
            LessonOnlineOrder lessonOnlineOrder = lessonOnlineOrderService.getOne(new LambdaQueryWrapper<LessonOnlineOrder>().eq(LessonOnlineOrder::getOrderSn,orderSn));

            if(lessonOnlineOrder == null){
                throw new FebsException("订单号不存在");
            }
            double payAmount = lessonOnlineOrder.getActualPayment();
            fee = (int)(payAmount*100);
            body = lessonOnlineOrder.getLessonName();
            customerId = lessonOnlineOrder.getCustomerId();
        } else if (orderSn.startsWith("LFN")) {
            LessonOfflineOrder lessonOfflineOrder = lessonOfflineOrderService.getOne(new LambdaQueryWrapper<LessonOfflineOrder>().eq(LessonOfflineOrder::getOrderSn,orderSn));

            if(lessonOfflineOrder == null){
                throw new FebsException("订单号不存在");
            }
            double payAmount = lessonOfflineOrder.getActualPayment();
            fee = (int)(payAmount*100);
            body = lessonOfflineOrder.getLessonName();
            customerId = lessonOfflineOrder.getCustomerId();
        } else if (orderSn.startsWith("CLN")) {
            CommuneLessonOrder communeLessonOrder = communeLessonOrderService.getOne(new LambdaQueryWrapper<CommuneLessonOrder>().eq(CommuneLessonOrder::getOrderSn,orderSn));

            if(communeLessonOrder == null){
                throw new FebsException("订单号不存在");
            }
            double payAmount = communeLessonOrder.getActualPayment();
            fee = (int)(payAmount*100);
            body = communeLessonOrder.getLessonName();
            customerId = communeLessonOrder.getCustomerId();
        } else if (orderSn.startsWith("CTN")) {
            CommuneTicketOrder communeTicketOrder = communeTicketOrderService.getOne(new LambdaQueryWrapper<CommuneTicketOrder>().eq(CommuneTicketOrder::getOrderSn,orderSn));

            if(communeTicketOrder == null){
                throw new FebsException("订单号不存在");
            }
            double payAmount = communeTicketOrder.getActualPayment();
            fee = (int)(payAmount*100);
            body = communeTicketOrder.getTicketName();
            customerId = communeTicketOrder.getCustomerId();
        } else {
            throw new FebsException("非法订单");
        }
        Customer customer = customerService.getById(customerId);
        if(customer == null){
            throw new FebsException("数据错误");
        }
        WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
        wxPayUnifiedOrderRequest.setBody(body);
        wxPayUnifiedOrderRequest.setOutTradeNo(orderSn);
        wxPayUnifiedOrderRequest.setTotalFee(fee);
        wxPayUnifiedOrderRequest.setOpenid(customer.getOpenId());
        return wxPayUnifiedOrderRequest;
    }

    @Override
    public void handleOrderNotifyResult(WxPayOrderNotifyResult notifyResult) {
        if(notifyResult.getReturnCode().equals("SUCCESS")) {
            if (notifyResult.getResultCode().equals("SUCCESS")) {
                String orderSn = notifyResult.getOutTradeNo();
                if (orderSn.startsWith("GBN")) {
                    goodsOrderService.update(new LambdaUpdateWrapper<GoodsOrder>().set(GoodsOrder::getOrderStatus, GoodsOrderStatus.待发货.state)
                            .set(GoodsOrder::getPayTime,new Date()).set(GoodsOrder::getModifyTime,new Date())
                            .eq(GoodsOrder::getOrderSn, orderSn));
                } else if (orderSn.startsWith("LON")) {
                    lessonOnlineOrderService.update(new LambdaUpdateWrapper<LessonOnlineOrder>().set(LessonOnlineOrder::getStatus, 1)
                            .set(LessonOnlineOrder::getPayTime,new Date()).set(LessonOnlineOrder::getModifyTime,new Date())
                            .eq(LessonOnlineOrder::getOrderSn, orderSn));
                } else if (orderSn.startsWith("LFN")) {
                    lessonOfflineOrderService.update(new LambdaUpdateWrapper<LessonOfflineOrder>().set(LessonOfflineOrder::getStatus, 1)
                            .set(LessonOfflineOrder::getPayTime,new Date()).set(LessonOfflineOrder::getModifyTime,new Date())
                            .eq(LessonOfflineOrder::getOrderSn, orderSn));

                    LessonOfflineOrder order = lessonOfflineOrderService.getOne(new LambdaQueryWrapper<LessonOfflineOrder>().eq(LessonOfflineOrder::getOrderSn, orderSn));
                    int lessonCount = order.getLessonCount()+order.getGiveLessonCount();
                    LessonOfflineStudentRecord lessonOfflineStudentRecord = new LessonOfflineStudentRecord(order.getLessonId(), order.getCustomerId(), order.getStudentId(), lessonCount, "缴费", new Date());
                    lessonOfflineStudentRecordService.save(lessonOfflineStudentRecord);
                } else if (orderSn.startsWith("CLN")) {
                    communeLessonOrderService.update(new LambdaUpdateWrapper<CommuneLessonOrder>().set(CommuneLessonOrder::getStatus, 1)
                            .set(CommuneLessonOrder::getPayTime,new Date()).set(CommuneLessonOrder::getModifyTime,new Date())
                            .eq(CommuneLessonOrder::getOrderSn, orderSn));
                }
                else if (orderSn.startsWith("CTN")) {
                    communeTicketOrderService.update(new LambdaUpdateWrapper<CommuneTicketOrder>().set(CommuneTicketOrder::getStatus, 1)
                            .set(CommuneTicketOrder::getPayTime,new Date()).set(CommuneTicketOrder::getModifyTime,new Date())
                            .eq(CommuneTicketOrder::getOrderSn, orderSn));
                }
            }
        }
    }
}
