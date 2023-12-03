package com.moil.hafen.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.moil.hafen.web.vo.AreaVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Slf4j
public class IpUtils {
    public static void main(String[] args) throws IOException {
        AreaVo areaVo = getAreaVo("180.149.130.16");
        System.out.println(JSONObject.toJSON(areaVo));
    }
    public static AreaVo getAreaVo(String ip){
        try {
            String result = sendGet("https://api01.aliyun.venuscn.com/ip", "ip="+ip);
            System.out.println("result===>:"+result);
            if(StringUtils.isNotBlank(result)){
                JSONObject jsonObject = JSONObject.parseObject(result);
                int status = jsonObject.getInteger("ret");
                if(status==200){
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    String province = jsonObject1.getString("region");
                    String city = jsonObject1.getString("city");
                    String district = jsonObject1.getString("district");
                    AreaVo areaVo = new AreaVo(province, city, district);
                    return areaVo;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String sendGet(String url, String param) throws IOException {
        String urlNameString = url;
        if (StringUtils.isNotBlank(param))
            urlNameString += "?" + param;
        URL realUrl = new URL(urlNameString);
        URLConnection connection = realUrl.openConnection();
        StringBuilder result = new StringBuilder();
        connection.setRequestProperty(USER_AGENT, USER_AGENT_VALUE);
        connection.setRequestProperty(CONNECTION, CONNECTION_VALUE);
        connection.setRequestProperty(ACCEPT, "*/*");
        connection.setRequestProperty("Authorization", "APPCODE fb6fe7b81708454ba82a97e210f836a8");
        connection.connect();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error("发送GET请求出现异常！", e);
        }
        return result.toString();
    }

    private static final String USER_AGENT = "user-agent";
    private static final String USER_AGENT_VALUE = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)";
    private static final String CONNECTION = "connection";
    private static final String CONNECTION_VALUE = "Keep-Alive";
    private static final String ACCEPT = "accept";
    private static final String UTF8 = "utf-8";
    private static final String ACCEPT_CHARSET = "Accept-Charset";
    private static final String CONTENTTYPE = "contentType";
    private static final String SSL = "ssl";

}
