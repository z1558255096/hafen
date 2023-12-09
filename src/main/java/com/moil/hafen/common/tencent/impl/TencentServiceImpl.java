package com.moil.hafen.common.tencent.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson.JSONObject;
import com.moil.hafen.common.constant.ThirdApiConstant;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.common.tencent.TencentService;
import com.moil.hafen.common.tencent.resp.TencentResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 陈子杰
 * @Description 腾讯地图服务实现类
 * @Version 1.0.0
 * @Date 2023/12/9 18:09
 */
@Service
@Slf4j
public class TencentServiceImpl implements TencentService {
    @Value("${tencent.key}")
    private String key;

    @Override
    public TencentResp suggestion(String keyword, String region) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("key", key);
        paramMap.put("keyword", keyword);
        paramMap.put("region", region);
        paramMap.put("region_fix", 1);
        paramMap.put("page_index", 1);
        paramMap.put("page_size", 20);
        String params = HttpUtil.toParams(paramMap);
        HttpRequest request = HttpUtil.createRequest(Method.GET, ThirdApiConstant.TENCENT_SUGGESTION + "?" + params);
        HttpResponse response = request.execute();
        JSONObject dataObject = JSONObject.parseObject(response.body());
        Integer status = (Integer) dataObject.get("status");
        if (status != 0) {
            log.error("腾讯地图API根据关键字获取提示调用失败，原因：{}", dataObject.get("message"));
            throw new FebsException("腾讯地图API调用失败");
        }
        TencentResp tencentResp = new TencentResp();
        tencentResp.setStatus(status);
        tencentResp.setMessage((String) dataObject.get("message"));
        tencentResp.setCount((Integer) dataObject.get("count"));
        tencentResp.setData(dataObject.get("data").toString());
        tencentResp.setRequestId((String) dataObject.get("request_id"));
        return tencentResp;
    }

    @Override
    public TencentResp location(String lat, String lng) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("key", key);
        paramMap.put("location", lat + "," + lng);
        String params = HttpUtil.toParams(paramMap);
        HttpRequest request = HttpUtil.createRequest(Method.GET, ThirdApiConstant.TENCENT_LOCATION + "?" + params);
        HttpResponse response = request.execute();
        JSONObject dataObject = JSONObject.parseObject(response.body());
        Integer status = (Integer) dataObject.get("status");
        if (status != 0) {
            log.error("腾讯地图API根据经纬度获取位置信息调用失败，原因：{}", dataObject.get("message"));
            throw new FebsException("腾讯地图API调用失败");
        }
        TencentResp tencentResp = new TencentResp();
        tencentResp.setStatus(status);
        tencentResp.setMessage((String) dataObject.get("message"));
        tencentResp.setCount((Integer) dataObject.get("count"));
        tencentResp.setData(dataObject.get("result").toString());
        tencentResp.setRequestId((String) dataObject.get("request_id"));
        return tencentResp;
    }
}
