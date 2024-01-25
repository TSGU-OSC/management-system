package com.example.controller;

import com.example.common.BaseResponse;
import com.example.common.ErrorCode;
import com.example.common.ResultUtils;
import com.example.exception.BusinessException;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.minio.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

import java.util.UUID;

/**
 * MinIO控制类
 *
 * @author lwy
 */
@ApiSupport(author = "lwy") // 接口作者
@Tag(name = "MinIO文件管理接口")   // 接口分组名
@Slf4j  // 日志
@CrossOrigin
@RequestMapping("/file")
@RestController
public class MinIOController {
    
    @Value("${minio.url}")
    private String minioUrl;
    
    @Value("${minio.access-key}")
    private String minioAccessKey;
    
    @Value("${minio.secret-key}")
    private String minioSecretKey;

    @Value("${minio.bucket}")
    private String minioBucket;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @Operation(description = "上传文件")
    public BaseResponse<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 创建MinIO客户端
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioUrl)
                    .credentials(minioAccessKey, minioSecretKey)
                    .build();
            
            // 生成随机文件名
            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
            
            // 使用putObject方法上传文件
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioBucket) // 存储桶名称
                    .object(filename)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
            
            return ResultUtils.success(filename);
        } catch (Exception e) {
            log.error("文件上传失败: "+e.getMessage());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"文件上传失败");
        }
    }

    /**
     *
     * @param filename
     * @return
     */
    @GetMapping("/read/{filename}")
    @Operation(description = "读取文件")
    public BaseResponse<byte[]> readFile(@PathVariable String filename) {
        try {
            // 创建MinIO客户端
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioUrl)
                    .credentials(minioAccessKey, minioSecretKey)
                    .build();

            // 获取文件流
            GetObjectResponse objectResponse = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(minioBucket) // 存储桶名称
                    .object(filename)
                    .build());
            byte[] bytes = objectResponse.readAllBytes();
            objectResponse.close();

            return ResultUtils.success(bytes);
        } catch (Exception e) {
            log.error("文件读取失败: "+e.getMessage());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"文件读取失败");
        }
    }

    /**
     * 删除文件
     *
     * @param filename
     * @return
     */
    @DeleteMapping("/delete/{filename}")
    @Operation(description = "删除文件")
    public BaseResponse<String> deleteFile(@PathVariable String filename) {
        try {
            // 创建MinIO客户端
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioUrl)
                    .credentials(minioAccessKey, minioSecretKey)
                    .build();

            // 删除文件
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioBucket) // 存储桶名称
                    .object(filename)
                    .build());

            return ResultUtils.success(filename);
        } catch (Exception e) {
            log.error("文件删除失败："+e.getMessage());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"文件删除失败");
        }
    }
}