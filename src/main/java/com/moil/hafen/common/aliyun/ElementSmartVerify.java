package com.moil.hafen.common.aliyun;

import com.aliyun.cloudauth20200618.models.ElementSmartVerifyResponse;
import com.aliyun.tea.*;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ElementSmartVerify {
    @Value("${aliyun.AccessKey}")
    private String accessKey;
    @Value("${aliyun.AccessSecret}")
    private String accessSecret;
    @Value("${aliyun.SceneId}")
    private long sceneId;
    /**
     * 使用AK&SK初始化账号Client
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.cloudauth20200618.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "cloudauth.aliyuncs.com";
        return new com.aliyun.cloudauth20200618.Client(config);
    }

    public ElementSmartVerifyResponse verify(String certUrl, String emblemUrl) throws Exception {
        com.aliyun.cloudauth20200618.Client client = ElementSmartVerify.createClient(accessKey, accessSecret);
        com.aliyun.cloudauth20200618.models.ElementSmartVerifyRequest elementSmartVerifyRequest = new com.aliyun.cloudauth20200618.models.ElementSmartVerifyRequest()
                .setSceneId(sceneId)
                .setOuterOrderNo(System.currentTimeMillis()+"")
                .setMode("OCR_VERIFY_ID_NAME")
                .setCertType("IDENTITY_CARD")
                .setCertUrl(certUrl)
                .setCertNationalEmblemUrl(emblemUrl);
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            ElementSmartVerifyResponse elementSmartVerifyResponse = client.elementSmartVerifyWithOptions(elementSmartVerifyRequest, runtime);
            log.error(new Gson().toJson(elementSmartVerifyResponse));
            return elementSmartVerifyResponse;
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
            return null;
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
            return null;
        }        
    }
}