package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.CommuneLessonOrder;

import java.util.List;

public interface CommuneLessonOrderService extends IService<CommuneLessonOrder> {
    CommuneLessonOrder createOrder(CommuneLessonOrder order);

    void afterSales(Integer orderId, String afterSalesReason);

    IPage<CommuneLessonOrder> getPage(QueryRequest request, CommuneLessonOrder communeLessonOrder);

    void refund(Integer id) throws WxPayException;

    List<CommuneLessonOrder> getCommuneLessonOrderList(CommuneLessonOrder communeLessonOrder);

    void use(Integer id);
}
