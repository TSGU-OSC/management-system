package com.example.controller;

import com.example.common.ResultUtils;
import com.example.config.MinIOProperty;
import com.example.enums.ErrorCodeEnum;
import com.example.exception.BusinessException;
import com.example.model.entity.User;
import com.example.model.vo.ResponseVO;
import com.example.service.UserService;
import com.example.utils.MinioUtil;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * MinIO控制类
 *
 * @author lwy
 */
@ApiSupport(author = "lwy") // 接口作者
@Tag(name = "MinIO管理接口")   // 接口分组名
@CrossOrigin
@RestController
@RequestMapping("/file")
public class MinIOController {

    @Resource
    MinIOProperty minioProperty = new MinIOProperty();
    @Resource
    MinioUtil minioUtil = new MinioUtil();
    @Resource
    UserService userService;

    /**
     * 上传文件
     */
    @Operation(description = "上传文件")
    @PostMapping("/upload")
    public ResponseVO<String> uploadFile(@RequestBody MultipartFile file, Long id) {
        if (file == null || id == null) {
            throw new BusinessException(ErrorCodeEnum.NULL_ERROR, "参数为空");
        }
        String fileName = minioUtil.minioUpload(file, minioProperty.getBucket());
        //同时将头像文件名存储到数据库中
        User user = userService.getById(id);
        user.setAvator(fileName);
        userService.updateUser(user);

        return ResultUtils.success(fileName);
    }

    /**
     * 下载文件
     */
    @Operation(description = "下载文件")
    @PostMapping("/download")
    public ResponseVO<InputStream> downloadFile(@RequestParam String fileName, HttpServletResponse response) {
        if (StringUtils.isBlank(fileName)) {
            throw new BusinessException(ErrorCodeEnum.NULL_ERROR, "参数为空");
        }
        InputStream inputStream = minioUtil.downloadFile(minioProperty.getBucket(), fileName, response);
        return ResultUtils.success(inputStream);
    }

    /**
     * 删除文件
     */
    @Operation(description = "删除文件")
    @PostMapping("/delete")
    public ResponseVO<String> deleteFile(String fileName, @RequestBody User user) {
        if (StringUtils.isBlank(fileName) || user == null) {
            throw new BusinessException(ErrorCodeEnum.NULL_ERROR, "参数为空");
        }
        minioUtil.deleteFile(minioProperty.getBucket(), fileName);
        // 同时删除数据库中的用户头像
        user.setAvator(null);
        userService.updateUser(user);

        return ResultUtils.success(fileName);
    }

}