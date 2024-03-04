package com.example.controller;

import com.alibaba.excel.EasyExcel;
import com.example.common.ResultUtils;
import com.example.config.ExcelListener;
import com.example.enums.ErrorCodeEnum;
import com.example.exception.BusinessException;
import com.example.model.dto.QueryDTO;
import com.example.model.dto.UserAddDTO;
import com.example.model.entity.ExcelInput;
import com.example.model.entity.User;
import com.example.model.vo.ResponseVO;
import com.example.service.ExcelService;
import com.example.service.UserService;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Excel服务控制类
 *
 * @author lwy
 */
@ApiSupport(author = "lwy") // 接口作者
@Tag(name = "Excel管理接口")   // 接口分组名
@Slf4j  // 日志
@RestController
// @CrossOrigin  // 支持跨域
@RequestMapping("/excel")
public class ExcelController {

    @Resource
    ExcelService excelService;

    /**
     * Excel导入用户
     */
    @PostMapping("/input")
    @Operation(description = "Excel导入用户")  // 接口信息描述
    public ResponseVO<String> excelInput(@RequestBody @NotNull MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), ExcelInput.class , new ExcelListener(excelService)).sheet().doRead();
        } catch (IOException e) {
            throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR,e.getMessage());
        }
        return ResultUtils.success("导入成功");
    }

    /**
     * Excel导出用户
     */
    @GetMapping("/output")
    @Operation(description = "Excel导出用户")  // 接口信息描述
    public void excelOutput(HttpServletResponse response) {
        excelService.excelOutput(response);
        log.info("Excel导出成功");
    }
}
