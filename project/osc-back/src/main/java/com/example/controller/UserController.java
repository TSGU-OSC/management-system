package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.BaseContext;
import com.example.common.BaseResponse;
import com.example.common.ErrorCode;
import com.example.common.ResultUtils;
import com.example.exception.BusinessException;
import com.example.model.dto.QueryDTO;
import com.example.model.entity.User;
import com.example.model.entity.VerifyCodeEntity;
import com.example.model.vo.UserAddVO;
import com.example.model.vo.UserLoginVO;
import com.example.service.UserService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.constants.UserContent.DEFAULT_USER;
import static com.example.constants.UserContent.SUPER_ADMIN_USER;

/**
 * 用户服务控制类
 *
 * @author lwy
 */
@ApiSupport(author = "lwy") // 接口作者
@Tag(name = "用户管理接口")   // 接口分组名
@Slf4j  // 日志
@RestController
@CrossOrigin  // 支持跨域
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    /**
     * 添加用户
     *
     * @param userAddVO
     * @return 新用户id
     */
    @PostMapping("/add")
    @Operation(description = "添加新用户")  // 接口信息描述
    public BaseResponse<Long> addUser(@RequestBody @Validated UserAddVO userAddVO) {
        if (userAddVO == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "参数为空");
        }
        // 获取当前线程用户
        Long currentId = BaseContext.getCurrentId();
        if (currentId == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "用户未登录");
        }
        User currentUser = userService.getById(currentId);
        // 权限需要高于被创建用户的权限
        if (currentUser.getRole() <= userAddVO.getRole()) {
            throw new BusinessException(ErrorCode.NO_AUTH, "权限不足");
        }
        long newUserId = userService.addUser(userAddVO);
        return ResultUtils.success(newUserId);
    }

    /**
     * 获取验证码
     *
     * @return
     */
    @PostMapping("/verify")
    @Operation(description = "获取验证码")
    public BaseResponse<VerifyCodeEntity> getVerifyCode() {
        VerifyCodeEntity verifyCode = userService.generateVerifyCode();
        return ResultUtils.success(verifyCode);
    }

    /**
     * 登录
     *
     * @param userLoginVO
     * @return
     */
    @PostMapping("/login")
    @Operation(description = "用户登录")  // 接口信息描述
    public BaseResponse<User> login(@RequestBody @Validated UserLoginVO userLoginVO, HttpServletRequest request) {
        if (userLoginVO == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "参数为空");
        }
        User user = userService.userLogin(userLoginVO, request);
        return ResultUtils.success(user);
    }

    /**
     * 登出
     *
     * @return
     */
    @PostMapping("/logout")
    @Operation(description = "用户退出")  // 接口信息描述
    public BaseResponse<String> logout(HttpServletRequest request) {
        userService.userLogout(request);
        return ResultUtils.success("登出成功");
    }

    /**
     * 获取当前用户
     *
     * @return 脱敏后的当前用户
     */
    @GetMapping("/current")
    @Operation(description = "获取当前登录用户")  // 接口信息描述
    public BaseResponse<User> getCurrentUser() {
        // 获取当前登录用户
        Long currentId = BaseContext.getCurrentId();
        if (currentId == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "用户未登录");
        }
        User currentUser = userService.getById(currentId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "用户不存在");
        }
        User safetyUser = userService.getSafetyUser(currentUser);
        return ResultUtils.success(safetyUser);
    }

    /**
     * 查询用户
     *
     * @param queryDTO
     * @return 用户列表
     */
    @PostMapping("/search")
    @Operation(description = "查询用户信息")  // 接口信息描述
    public BaseResponse<List<User>> searchUsers(@RequestBody QueryDTO queryDTO) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 非空查询
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(queryDTO.getCode()), User::getCode, queryDTO.getCode())
                .like(StringUtils.isNotEmpty(queryDTO.getMajor()), User::getMajor, queryDTO.getMajor())
                .like(StringUtils.isNotEmpty(queryDTO.getName()), User::getName, queryDTO.getName())
                .like(StringUtils.isNotEmpty(queryDTO.getCity()), User::getCity, queryDTO.getCity())
                .like(StringUtils.isNotEmpty(queryDTO.getAcademy()), User::getAcademy, queryDTO.getAcademy())
                .like(StringUtils.isNotEmpty(queryDTO.getPhone()), User::getPhone, queryDTO.getPhone())
                .like(StringUtils.isNotEmpty(queryDTO.getProvince()), User::getProvince, queryDTO.getProvince())
                .like(queryDTO.getDuty() != null, User::getDuty, queryDTO.getDuty())
                .like(queryDTO.getClazz() != null, User::getClazz, queryDTO.getClazz())
                .like(queryDTO.getStatus() != null, User::getStatus, queryDTO.getStatus())
                .like(queryDTO.getRole() != null, User::getRole, queryDTO.getRole());

        List<User> list = userService.list(lambdaQueryWrapper);
        // 如果用户权限低，对查询到的用户进行脱敏
        Long currentId = BaseContext.getCurrentId();
        if (userService.getById(currentId).getRole() == DEFAULT_USER) {
            list = list.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        }

        return ResultUtils.success(list);
    }

    /**
     * 根据ID删除用户
     *
     * @param id 需要删除的用户id
     * @return 被删除的用户id
     */
    @PostMapping("/delete")
    @Operation(description = "删除用户")  // 接口信息描述
    public BaseResponse<Long> deleteUser(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }
        // 获取当前登录用户
        Long currentId = BaseContext.getCurrentId();
        User user = userService.getById(currentId);
        // 只能删除权限低于自己的用户
        if (user.getRole() <= userService.getById(id).getRole()) {
            throw new BusinessException(ErrorCode.NO_AUTH, "用户权限不足");
        }
        // 尝试删除用户
        boolean result = userService.removeById(id);
        // 删除失败
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除失败");
        }
        // 删除成功
        return ResultUtils.success(id);
    }

    /**
     * 更新/修改用户
     *
     * @param user
     * @return
     */
    @PostMapping("/update")
    @Operation(description = "更新/修改用户")  // 接口信息描述
    public BaseResponse<User> updateUser(@RequestBody User user) {
        // 获取当前登录用户
        Long currentUserId = BaseContext.getCurrentId();
        User currentUser = userService.getById(currentUserId);
        // 学号不能修改
        if (!Objects.equals(user.getCode(), userService.getById(user.getId()).getCode())) {
            throw new BusinessException(ErrorCode.NO_AUTH, "学号不可修改");
        }
        // 如果是修改自己的信息
        if (currentUserId.equals(user.getId())) {
            // 超级管理员才能修改自己的 role status duty
            if (currentUser.getRole() != SUPER_ADMIN_USER &&
                    (!currentUser.getRole().equals(user.getRole()) ||
                            !currentUser.getStatus().equals(user.getStatus()) ||
                            !currentUser.getDuty().equals(user.getDuty()))) {
                throw new BusinessException(ErrorCode.NO_AUTH, "权限不足");
            }
        } else {  // 如果不是修改自己的信息
            // 超管才能修改他人的 role status duty ，除非修改者的权限高于被修改者，
            // 并且修改者不能将被修改者的权限改至高于等于修改者的权限
            if (currentUser.getRole() != SUPER_ADMIN_USER &&
                    currentUser.getRole() <=
                            (Math.max(user.getRole(), userService.getById(user.getId()).getRole()))) {
                throw new BusinessException(ErrorCode.NO_AUTH, "权限不足");
            }
        }
        // 权限足够，可以修改
        boolean result = userService.updateById(user);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "修改失败");
        }
        return ResultUtils.success(userService.getById(user.getId()));
    }
}
