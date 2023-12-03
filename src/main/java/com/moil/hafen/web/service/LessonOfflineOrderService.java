package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.LessonOfflineOrder;

import java.util.List;

public interface LessonOfflineOrderService extends IService<LessonOfflineOrder> {
    LessonOfflineOrder createOrder(LessonOfflineOrder order);

    void afterSales(Integer orderId, String afterSalesReason);

    IPage<LessonOfflineOrder> getPage(QueryRequest request, LessonOfflineOrder lessonOfflineOrder);

    void refund(Integer id) throws WxPayException;

    List<LessonOfflineOrder> getlessonOfflineOrderList(LessonOfflineOrder lessonOfflineOrder);
}
