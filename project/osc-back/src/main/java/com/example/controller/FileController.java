package com.example.controller;

import com.example.common.ResultUtils;
import com.example.enums.ErrorCodeEnum;
import com.example.exception.BusinessException;
import com.example.model.entity.User;
import com.example.model.vo.ResponseVO;
import com.example.service.FileService;
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
public class FileController {

    @Resource
    FileService fileService;

    /**
     * 上传文件
     */
    @Operation(description = "上传文件")
    @PostMapping("/upload")
    public ResponseVO<String> uploadFile(@RequestBody MultipartFile file, Long id) {
        if (file == null || id == null) {
            throw new BusinessException(ErrorCodeEnum.NULL_ERROR, "参数为空");
        }
        String fileName = fileService.uploadFile(file, id);
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
        InputStream inputStream = fileService.downloadFile(fileName, response);
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
        fileService.deleteFile(fileName, user);
        return ResultUtils.success(fileName);
    }

}