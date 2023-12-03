package com.moil.hafen.common.aliyun;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * 短信服务
 * Created by aqlu on 15/12/4.
 */
@Component
@Slf4j
public class SmsSend {
    @Value("${aliyun.AccessKey}")
    private String accessKey;
    @Value("${aliyun.AccessSecret}")
    private String accessSecret;
    @Value("${aliyun.TemplateCode}")
    private String templateCode;
    @Value("${aliyun.TemplateSign}")
    private String templateSign;

    public SendSmsResponse sendSms(String mobile,String code) throws Exception {

        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(accessKey)
                .accessKeySecret(accessSecret)
                .build());

        // Configure the Client
        AsyncClient client = AsyncClient.builder()
                .region("cn-hangzhou")
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")
                )
                .build();

        // Parameter settings for API request
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .phoneNumbers(mobile)
                .signName(templateSign)
                .templateCode(templateCode)
                .templateParam("{\"code\":\""+code+"\"}")
                // Request-level configuration rewrite, can set Http request parameters, etc.
                // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                .build();

        // Asynchronously get the return value of the API request
        CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
        SendSmsResponse resp = response.get();
        log.error(mobile+":{}",new Gson().toJson(resp));
        // Finally, close the client
        client.close();
        return resp;
    }
}
