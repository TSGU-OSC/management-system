package com.example.controller;

import com.example.common.ResultUtils;
import com.example.enums.ErrorCodeEnum;
import com.example.exception.BusinessException;
import com.example.model.dto.UserLoginDTO;
import com.example.model.dto.VerifyCodeDTO;
import com.example.model.entity.User;
import com.example.model.vo.ResponseVO;
import com.example.service.LoginService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户登录控制类
 *
 * @author lwy
 */
@ApiSupport(author = "lwy") // 接口作者
@Tag(name = "用户登录接口")   // 接口分组名
@Slf4j  // 日志
@RestController
// @CrossOrigin  // 支持跨域
@RequestMapping("/user")
public class LoginController {

    @Resource
    LoginService loginService;

    /**
     * 登录
     *
     * @param userLoginDTO 用户登录DTO类
     * @return 用户信息
     */
    @PostMapping("/login")
    @Operation(description = "用户登录")  // 接口信息描述
    public ResponseVO<User> login(@RequestBody @Validated UserLoginDTO userLoginDTO, HttpServletRequest request) {
        User user = loginService.userLogin(userLoginDTO, request);
        return ResultUtils.success(user);
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    @Operation(description = "用户退出")  // 接口信息描述
    public ResponseVO<String> logout(HttpServletRequest request) {
        loginService.userLogout(request);
        return ResultUtils.success("登出成功");
    }

    /**
     * 获取验证码
     *
     * @return 验证码DTO类
     */
    @PostMapping("/verify")
    @Operation(description = "获取验证码")
    public ResponseVO<VerifyCodeDTO> getVerifyCode() {
        VerifyCodeDTO verifyCode = loginService.generateVerifyCode();
        return ResultUtils.success(verifyCode);
    }
}
