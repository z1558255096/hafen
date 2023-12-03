package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.CommuneTicketOrder;

import java.util.List;

public interface CommuneTicketOrderService extends IService<CommuneTicketOrder> {
    CommuneTicketOrder createOrder(CommuneTicketOrder order);

    void afterSales(Integer orderId, String afterSalesReason);

    IPage<CommuneTicketOrder> getPage(QueryRequest request, CommuneTicketOrder communeTicketOrder);

    void refund(Integer id) throws WxPayException;

    List<CommuneTicketOrder> getCommuneTicketOrderList(CommuneTicketOrder communeTicketOrder);

    void use(Integer id);
}
