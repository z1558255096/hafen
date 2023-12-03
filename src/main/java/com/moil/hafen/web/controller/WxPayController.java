package com.moil.hafen.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.common.utils.IPUtil;
import com.moil.hafen.web.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * @author Binary Wang
 */
@Api(tags = "前端-支付")
@RestController
@RequestMapping("/pay")
@Slf4j
public class WxPayController {
  @Resource
  private WxPayService wxService;
  @Resource
  private OrderService orderService;
  @Value("${wx.pay.notifyUrl}")
  private String notifyUrl;

  /**
   * 调用统一下单接口，并组装生成支付所需参数对象.
   *
   * @param request 统一下单请求参数
   * @param <T>     请使用{@link com.github.binarywang.wxpay.bean.order}包下的类
   * @return 返回 {@link com.github.binarywang.wxpay.bean.order}包下的类对象
   */
  @ApiOperation(value = "前端-调起微信支付")
  @PostMapping("/pay")
  @ApiImplicitParam(value = "订单号",name = "orderSn",required = true)
  public Result pay(String orderSn, HttpServletRequest request) {
    try {
      WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = orderService.createWxOrder(orderSn);
      String ip = IPUtil.getIpAddr(request);
      wxPayUnifiedOrderRequest.setSpbillCreateIp(ip);
      wxPayUnifiedOrderRequest.setNotifyUrl(notifyUrl+"/pay/notify/order");
      wxPayUnifiedOrderRequest.setTradeType("JSAPI");
      JSONObject object = new JSONObject();
      object.put("result",this.wxService.createOrder(wxPayUnifiedOrderRequest));
      return Result.OK(object.get("result"));
    } catch (FebsException e) {
      log.error("微信创建订单失败",e);
      return Result.error(e.getMessage());
    } catch (WxPayException e) {
      log.error("微信创建订单失败",e);
      return Result.error(e.getCustomErrorMsg());
    } catch (Exception e){
      String msg = "微信创建订单失败";
      log.error(msg,e);
      return Result.error(msg);
    }

  }
  @ApiOperation(value = "支付回调通知处理")
  @PostMapping("/notify/order")
  public String parseOrderNotifyResult(@RequestBody String xmlData) throws WxPayException {
    log.error("微信回调：{}",xmlData);
    final WxPayOrderNotifyResult notifyResult = this.wxService.parseOrderNotifyResult(xmlData);
    orderService.handleOrderNotifyResult(notifyResult);
    return WxPayNotifyResponse.success("成功");
  }

}