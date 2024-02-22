package com.example.controller;

import com.example.common.ResultUtils;
import com.example.model.dto.QueryDTO;
import com.example.model.dto.UserAddDTO;
import com.example.model.entity.User;
import com.example.model.vo.ResponseVO;
import com.example.service.UserService;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
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
// @CrossOrigin  // 支持跨域
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
        User currentUser = userService.getCurrentUser();
        return ResultUtils.success(currentUser);
    }

    /**
     * 查询用户
     *
     * @param queryDTO   查询用户DTO类
     * @param pageNumber 页码
     * @param pageSize   每页数目
     * @return 用户列表
     */
    @PostMapping("/search")
    @Operation(description = "查询用户信息")  // 接口信息描述
    public ResponseVO<PageInfo<User>> listUsers(@RequestBody QueryDTO queryDTO,Integer pageNumber,Integer pageSize) {
        PageInfo<User> userList = userService.listUsers(queryDTO, pageNumber, pageSize);
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
    public ResponseVO<Long> deleteUser(@NotNull Long id) {
        Long userId = userService.deleteUser(id);
        return ResultUtils.success(userId);
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
