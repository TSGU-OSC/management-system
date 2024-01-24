package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.BaseResponse;
import com.example.common.ErrorCode;
import com.example.common.ResultUtils;
import com.example.exception.BusinessException;
import com.example.model.domain.NewUser;
import com.example.model.domain.request.UserLoginRequest;
import com.example.service.NewUserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.content.UserContent.DEFAULT_USER;
import static com.example.content.UserContent.USER_LOGIN_STATE;

/**
 * 用户服务控制类
 *
 * @author lwy
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    NewUserService newUserService;
/*
    @PostMapping("/register")
    public BaseResponse<Long> register(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest==null) {
            throw new BusinessException(ErrorCode.NULL_ERROR,"参数为空");
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword,planetCode)){
            throw new BusinessException(ErrorCode.NULL_ERROR,"参数为空");
        }
        Long result= newUserService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        return ResultUtils.success(result);
    }*/

    /**
     * 登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<NewUser> login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "参数为空");
        }
        String code = userLoginRequest.getCode();
        String password = userLoginRequest.getPassword();
        if (StringUtils.isAnyBlank(code, password)) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "学号或密码为空");
        }
        NewUser user = newUserService.userLogin(code, password, request);
        return ResultUtils.success(user);
    }

    /**
     * 登出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> logout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "参数为空");
        }
        int result = newUserService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前用户
     *
     * @param request
     * @return
     */
    @GetMapping("/current")
    public BaseResponse<NewUser> getCurrentUser(HttpServletRequest request) {
        // 从登录态中获取当前登录用户
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        NewUser currentUser = (NewUser) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "用户未登录");
        }
        Integer userId = currentUser.getId();
        NewUser user = newUserService.getById(userId);
        NewUser safetyUser = newUserService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    /**
     * 搜索用户
     *
     * @param code    学号
     * @param request
     * @return
     */
    @GetMapping("/search")
    public BaseResponse<List<NewUser>> searchUsers(String code, HttpServletRequest request) {
        QueryWrapper<NewUser> queryWrapper = new QueryWrapper<>();
        // 学号非空
        if (StringUtils.isNotBlank(code)) {
            // 模糊查询
            queryWrapper.like("code", code);
        }
        List<NewUser> list = newUserService.list(queryWrapper);
        // 对查询到的用户进行脱敏
        List<NewUser> collect = list.stream().map(user -> newUserService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(collect);
    }

    /**
     * 根据ID删除用户
     *
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody Long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "用户不是管理员");
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }
        boolean result = newUserService.removeById(id);
        return ResultUtils.success(result);
    }

    /**
     * 鉴权
     *
     * @param request
     * @return 不是普通成员就返回true
     */
    private boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        NewUser user = (NewUser) userObj;
        return user != null && user.getDuty() != DEFAULT_USER;
    }
}
