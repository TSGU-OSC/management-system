package com.example.controller;

import com.example.common.BaseContext;
import com.example.common.ResultUtils;
import com.example.enums.ErrorCodeEnum;
import com.example.exception.BusinessException;
import com.example.model.dto.QueryDTO;
import com.example.model.dto.UserAddDTO;
import com.example.model.entity.User;
import com.example.model.vo.ResponseVO;
import com.example.service.UserService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
     * @param userAddDTO 用户添加DTO类
     * @return 新用户id
     */
    @PostMapping("/add")
    @Operation(description = "添加新用户")  // 接口信息描述
    public ResponseVO<Long> addUser(@RequestBody @Validated UserAddDTO userAddDTO) {
        if (userAddDTO == null) {
            throw new BusinessException(ErrorCodeEnum.NULL_ERROR, "参数为空");
        }
        // 获取当前线程用户
        Long currentId = BaseContext.getCurrentId();
        if (currentId == null) {
            throw new BusinessException(ErrorCodeEnum.NULL_ERROR, "用户未登录");
        }
        User currentUser = userService.getById(currentId);
        // 权限需要高于被创建用户的权限
        if (currentUser.getRole() <= userAddDTO.getRole()) {
            throw new BusinessException(ErrorCodeEnum.NO_AUTH, "权限不足");
        }
        long newUserId = userService.addUser(userAddDTO);
        return ResultUtils.success(newUserId);
    }

    /**
     * 获取当前用户
     *
     * @return 脱敏后的当前用户
     */
    @GetMapping("/current")
    @Operation(description = "获取当前登录用户")  // 接口信息描述
    public ResponseVO<User> getCurrentUser() {
        // 获取当前登录用户
        Long currentId = BaseContext.getCurrentId();
        if (currentId == null) {
            throw new BusinessException(ErrorCodeEnum.NOT_LOGIN, "用户未登录");
        }
        User currentUser = userService.getById(currentId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCodeEnum.NOT_LOGIN, "用户不存在");
        }
        User safetyUser = userService.getSafetyUser(currentUser);
        return ResultUtils.success(safetyUser);
    }

    /**
     * 查询用户
     *
     * @param queryDTO 查询用户DTO类
     * @return 用户列表
     */
    @PostMapping("/search")
    @Operation(description = "查询用户信息")  // 接口信息描述
    public ResponseVO<List<User>> listUsers(@RequestBody QueryDTO queryDTO) {
        List<User> userList = userService.listUsers(queryDTO);
        return ResultUtils.success(userList);
    }

    /**
     * 根据ID删除用户
     *
     * @param id 需要删除的用户id
     * @return 被删除的用户id
     */
    @PostMapping("/delete")
    @Operation(description = "删除用户")  // 接口信息描述
    public ResponseVO<Long> deleteUser(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCodeEnum.PARAMS_ERROR, "参数错误");
        }
        // 获取当前登录用户
        Long currentId = BaseContext.getCurrentId();
        User user = userService.getById(currentId);
        // 只能删除权限低于自己的用户
        if (user.getRole() <= userService.getById(id).getRole()) {
            throw new BusinessException(ErrorCodeEnum.NO_AUTH, "用户权限不足");
        }
        // 删除用户
        userService.removeById(id);
        // 删除成功
        return ResultUtils.success(id);
    }

    /**
     * 更新/修改用户
     *
     * @param user 需要更改的用户
     * @return 更改后的用户
     */
    @PostMapping("/update")
    @Operation(description = "更新/修改用户")  // 接口信息描述
    public ResponseVO<User> updateUser(@RequestBody User user) {
        return ResultUtils.success(userService.updateUser(user));
    }

    /**
     * 统计总人数
     */
    @GetMapping("/count/sum")
    @Operation(description = "统计总人数")
    public ResponseVO<Long> countSum() {
        long count = userService.count();
        return ResultUtils.success(count);
    }

    /**
     * 统计男/女人数
     */
    @GetMapping("/count/gender")
    @Operation(description = "统计男/女人数 1-男 0-女")
    public ResponseVO<Long> countGender(int gender) {
        long count = userService.countGender(gender);
        return ResultUtils.success(count);
    }

    /**
     * 统计各省人数
     */
    @GetMapping("/count/province")
    @Operation(description = "统计各省人数")
    public ResponseVO<List<Map<String, Object>>> countProvince() {
        List<Map<String, Object>> maps = userService.countProvince();
        return ResultUtils.success(maps);
    }
}
