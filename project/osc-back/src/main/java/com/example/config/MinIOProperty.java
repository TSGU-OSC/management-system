package com.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * MinIO 存储属性类
 *
 * @author lwy
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinIOProperty {
    private String url;
    private String accessKey;
    private String secretKey;
    private String bucket;
}