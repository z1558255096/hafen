package com.moil.hafen.web.controller;

import com.google.gson.Gson;
import com.kuaidi100.sdk.api.Subscribe;
import com.kuaidi100.sdk.contant.ApiInfoConstant;
import com.kuaidi100.sdk.contant.CompanyConstant;
import com.kuaidi100.sdk.core.IBaseClient;
import com.kuaidi100.sdk.request.SubscribeParam;
import com.kuaidi100.sdk.request.SubscribeParameters;
import com.kuaidi100.sdk.request.SubscribeReq;
import com.kuaidi100.sdk.response.SubscribePushParamResp;
import com.kuaidi100.sdk.response.SubscribePushResult;
import com.kuaidi100.sdk.response.SubscribeResp;
import com.kuaidi100.sdk.utils.PropertiesReader;
import com.kuaidi100.sdk.utils.SignUtils;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.service.GoodsOrderLogisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"/kuaidi100"})
@Api(tags = "快递100同步")
public class LogisticsSyncController extends BaseController {

    private String message;

    @Resource
    private GoodsOrderLogisticsService goodsOrderLogisticsService;



    @PostMapping("/callbackurl")
    @ApiOperation("快递100回调")
    public SubscribeResp callbackUrl(HttpServletRequest request) throws FebsException {
        String param = request.getParameter("param");
        String sign = request.getParameter("sign");
        //建议记录一下这个回调的内容，方便出问题后双方排查问题
        //log.debug("快递100订阅推送回调结果|{}|{}",param,sign);
        //订阅时传的salt,没有可以忽略
        String salt = "";
        String ourSign = SignUtils.sign(param + salt);
        SubscribeResp subscribeResp = new SubscribeResp();
        subscribeResp.setResult(Boolean.TRUE);
        subscribeResp.setReturnCode("200");
        subscribeResp.setMessage("成功");
        //加密如果相等，属于快递100推送；否则可以忽略掉当前请求
        if (ourSign.equals(sign)){
            //TODO 业务处理
            SubscribePushParamResp subscribePushParamResp = new Gson().fromJson(param, SubscribePushParamResp.class);
            SubscribePushResult lastResult = subscribePushParamResp.getLastResult();
//            goodsOrderLogisticsService.save();
            return subscribeResp;
        }
        return null;
    }
    String key = PropertiesReader.get("key");
    String customer = PropertiesReader.get("customer");
    String secret = PropertiesReader.get("secret");
    String siid = PropertiesReader.get("siid");
    String userid = PropertiesReader.get("userid");
    String tid = PropertiesReader.get("tid");
    String secret_key = PropertiesReader.get("secret_key");
    String secret_secret = PropertiesReader.get("secret_secret");
    public void testSubscribe() throws Exception{
        SubscribeParameters subscribeParameters = new SubscribeParameters();
        subscribeParameters.setCallbackurl("http://www.baidu.com");
        subscribeParameters.setPhone("17725390266");

        SubscribeParam subscribeParam = new SubscribeParam();
        subscribeParam.setParameters(subscribeParameters);
        subscribeParam.setCompany(CompanyConstant.ST);
        subscribeParam.setNumber("773039762404825");
        subscribeParam.setKey(key);

        SubscribeReq subscribeReq = new SubscribeReq();
        subscribeReq.setSchema(ApiInfoConstant.SUBSCRIBE_SCHEMA);
        subscribeReq.setParam(new Gson().toJson(subscribeParam));

        IBaseClient subscribe = new Subscribe();
        System.out.println(subscribe.execute(subscribeReq));
    }

}
