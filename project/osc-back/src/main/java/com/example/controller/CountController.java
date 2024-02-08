package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.BaseResponse;
import com.example.common.ResultUtils;
import com.example.model.entity.User;
import com.example.service.UserService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 数据统计控制类
 *
 * @author lwy
 */
@ApiSupport(author = "lwy") // 接口作者
@Tag(name = "数据统计接口")   // 接口分组名
@CrossOrigin
@RestController
@RequestMapping("/count")
public class CountController {
    @Resource
    UserService userService;

    /**
     * 统计总人数
     */
    @GetMapping("/sum")
    @Operation(description = "统计总人数")
    public BaseResponse<Long> CountSum() {
        long count = userService.count();
        return ResultUtils.success(count);
    }

    /**
     * 统计男/女人数
     */
    @GetMapping("/gender")
    @Operation(description = "统计男/女人数 1-男 0-女")
    public BaseResponse<Long> CountGender(int gender) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gender", gender);
        long count = userService.count(queryWrapper);
        return ResultUtils.success(count);
    }

    /**
     * 统计各省人数
     */
    @GetMapping("/province")
    @Operation(description = "统计各省人数")
    public BaseResponse<List> CountProvince() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(*) as count,province").groupBy("province");
        List<Map<String, Object>> maps = userService.listMaps(queryWrapper);
        return ResultUtils.success(maps);
    }
}
