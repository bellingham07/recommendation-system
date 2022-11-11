package com.example.common.utils.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="oss")
@Component
@Data
public class OssProperties {
    private String endpoint;
    private String keyId;
    private String keySecret;
    private String bucketName;
    private String fileHost;
}
