package com.moil.hafen.common.config;

import com.moil.hafen.common.enums.CloudService;
import com.moil.hafen.common.factory.CloudStorageConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "oss.config")
@Data
public class OssCofig {
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String domain;
    private String endPoint;
    private String prefix;


    @Bean
    public CloudStorageConfig config() {
        CloudStorageConfig config = new CloudStorageConfig();
        config.setAliyunAccessKeyId(accessKeyId);
        config.setAliyunAccessKeySecret(accessKeySecret);
        config.setAliyunBucketName(bucketName);
        config.setAliyunDomain(domain);
        config.setAliyunEndPoint(endPoint);
        config.setAliyunPrefix(prefix);
        config.setType( CloudService.ALIYUN.getValue());
        return config;
    }
}
