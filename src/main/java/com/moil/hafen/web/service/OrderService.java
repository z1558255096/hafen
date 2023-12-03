package com.moil.hafen.web.service;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.moil.hafen.common.exception.FebsException;

public interface OrderService {

    WxPayUnifiedOrderRequest createWxOrder(String orderSn) throws FebsException;

    void handleOrderNotifyResult(WxPayOrderNotifyResult notifyResult);
}
