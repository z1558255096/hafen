package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.GoodsOrder;
import com.moil.hafen.web.domain.GoodsOrderLogistics;

import java.util.List;

public interface GoodsOrderService extends IService<GoodsOrder> {
    IPage<GoodsOrder> getPage(QueryRequest request, GoodsOrder goodsOrder);

    List<GoodsOrder> getGoodsOrderList(GoodsOrder goodsOrder);

    void delivery(Integer id, String logisticsSn, String deliveryCode) throws Exception;

    GoodsOrder detail(Integer id);

    void afterSalesApproval(Integer id, String status, String reason) throws WxPayException;

    void refund(Integer id) throws WxPayException;

    GoodsOrder createOrder(GoodsOrder order);

    void afterSales(Integer orderId, int afterSalesType, String afterSalesReason, String afterSalesCertificate);

    void updateAfterSalesLogisticsSn(Integer orderId, String logisticsSn);

    void confirmReceipt(Integer orderId);

    List<GoodsOrderLogistics> getGoodsOrderLogistics(Integer orderId);
}
