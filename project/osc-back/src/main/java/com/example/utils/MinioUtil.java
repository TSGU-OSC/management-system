package com.example.utils;

import com.example.enums.ErrorCodeEnum;
import com.example.exception.BusinessException;
import io.minio.*;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


/**
 * MinIO工具类
 *
 * @author lwy
 */
@Slf4j
@Component
public class MinioUtil {

    @Resource
    private MinioClient minioClient;

    /**
     * Minio文件上传
     *
     * @param file       文件实体
     * @param bucketName 所存文件夹（桶名）
     * @return 上传的文件名
     */
    public String minioUpload(MultipartFile file, String bucketName) {
        try {
            //给文件生成一个唯一名称  当日日期-uuid.后缀名
            String folderName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
            String fileName = String.valueOf(UUID.randomUUID());
            String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));//文件后缀名
            String objectName = folderName + "-" + fileName + extName;

            InputStream inputStream = file.getInputStream();
            PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(bucketName).object(objectName)
                    .stream(inputStream, file.getSize(), -1).contentType(file.getContentType()).build();
            //文件名称相同会覆盖
            minioClient.putObject(objectArgs);
            return objectName;
        } catch (Exception e) {
            log.error("文件上传失败: " + e);
            throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR, "文件上传失败");
        }
    }

    /**
     * 下载文件
     */
    public void downloadFile(String bucketName, String fileName, HttpServletResponse response) {
        if (StringUtils.isBlank(fileName)) {
            throw new BusinessException(ErrorCodeEnum.NULL_ERROR, "参数为空");
        }
        try {
            InputStream file = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    URLEncoder.encode(fileName.substring(fileName.lastIndexOf("/") + 1), "UTF-8"));
            response.setContentType("application/octet-stream");
//            response.setContentType("img/jpg");
            response.setCharacterEncoding("UTF-8");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = file.read(buffer)) > 0) {
                servletOutputStream.write(buffer, 0, len);
            }
            servletOutputStream.flush();
            file.close();
            servletOutputStream.close();
        } catch (Exception e) {
            log.error("文件名: "+ fileName+"下载文件时出现异常: " + e);
            throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR, "下载文件时出现异常");
        }
    }

    /**
     * 删除文件
     */
    public void deleteFile(String bucketName, String fileName) {
        try {
            if (StringUtils.isBlank(fileName)) {
                throw new BusinessException(ErrorCodeEnum.PARAMS_ERROR, "参数为空");
            }
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (isExist) {
                minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
            } else {
                throw new BusinessException(ErrorCodeEnum.PARAMS_ERROR, "桶不存在");
            }
        } catch (Exception e) {
            log.error("删除桶内所有文件时出现异常: " + e.getMessage());
            throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR, "删除桶内所有文件时出现异常");
        }
    }

}