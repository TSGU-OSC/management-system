package com.example.controller;

import com.example.common.ResultUtils;
import com.example.model.entity.User;
import com.example.model.vo.ResponseVO;
import com.example.service.FileService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
// @CrossOrigin
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
    public ResponseVO<String> uploadFile(@RequestBody @NotNull MultipartFile file, @NotNull Long id) {
        String fileName = fileService.uploadFile(file, id);
        return ResultUtils.success(fileName);
    }

    /**
     * 下载文件
     */
    @Operation(description = "下载文件")
    @GetMapping("/download")
    public void downloadFile(@NotBlank String fileName, HttpServletResponse response) {
        fileService.downloadFile(fileName, response);
    }

    /**
     * 删除文件
     */
    @Operation(description = "删除文件")
    @PostMapping("/delete")
    public ResponseVO<String> deleteFile(@NotBlank String fileName, @RequestBody @NotNull User user) {
        fileService.deleteFile(fileName, user);
        return ResultUtils.success(fileName);
    }

}