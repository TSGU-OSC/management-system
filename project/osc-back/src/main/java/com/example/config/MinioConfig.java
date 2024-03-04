package com.example.config;

import io.minio.MinioClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MinIO配置类
 *
 * @author lwy
 */
@Slf4j
@Configuration
public class MinioConfig {

    @Resource
    private MinIOProperty minioProperty;

    /**
     * 初始化minio配置
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioProperty.getUrl()) //操作MinIO地址 端口
                .credentials(minioProperty.getAccessKey(), minioProperty.getSecretKey())
                .build();
    }

}