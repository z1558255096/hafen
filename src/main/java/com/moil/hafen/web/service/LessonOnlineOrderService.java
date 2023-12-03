package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.LessonOnlineOrder;

import java.util.List;

public interface LessonOnlineOrderService extends IService<LessonOnlineOrder> {
    LessonOnlineOrder createOrder(LessonOnlineOrder order);

    void afterSales(Integer orderId, String afterSalesReason);

    IPage<LessonOnlineOrder> getPage(QueryRequest request, LessonOnlineOrder lessonOnlineOrder);

    void refund(Integer id) throws WxPayException;

    List<LessonOnlineOrder> getlessonOnlineOrderList(LessonOnlineOrder lessonOnlineOrder);
}
