package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.BaseContext;
import com.example.common.BaseResponse;
import com.example.common.ErrorCode;
import com.example.common.ResultUtils;
import com.example.exception.BusinessException;
import com.example.model.entity.Announcement;
import com.example.model.entity.User;
import com.example.model.vo.AnnouncementAddVO;
import com.example.service.AnnouncementService;
import com.example.service.UserService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.constants.UserContent.DEFAULT_USER;

/**
 * 公告服务控制类
 *
 * @author lwy
 */
@ApiSupport(author = "lwy") // 接口作者
@Tag(name = "公告管理接口")   // 接口分组名
@Slf4j  // 日志
@RestController
@CrossOrigin  // 支持跨域
@RequestMapping("/announce")
public class AnnouncementController {

    @Resource
    AnnouncementService announcementService;
    @Resource
    UserService userService;

    /**
     * 添加公告
     *
     * @return 新公告id
     */
    @PostMapping("/add")
    @Operation(description = "添加新公告")  // 接口信息描述
    public BaseResponse<Long> addAnnouncement(@RequestBody @Validated AnnouncementAddVO announcementAddVO) {
        if (announcementAddVO == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "参数为空");
        }
        // 获取当前线程用户
        Long currentId = BaseContext.getCurrentId();
        if (currentId == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "用户未登录");
        }
        User currentUser = userService.getById(currentId);
        // 鉴权
        if (currentUser.getRole() <= DEFAULT_USER) {
            throw new BusinessException(ErrorCode.NO_AUTH, "权限不足");
        }
        long newAnnouncementId = announcementService.addAnnouncement(announcementAddVO);
        return ResultUtils.success(newAnnouncementId);
    }

    /**
     * 查询公告
     *
     * @return 公告列表
     */
    @GetMapping("/search")
    @Operation(description = "根据标题查询公告信息")  // 接口信息描述
    public BaseResponse<List<Announcement>> searchAnnouncements(String title) {
        // 模糊匹配
        LambdaQueryWrapper<Announcement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Announcement::getTitle, title);

        List<Announcement> list = announcementService.list(queryWrapper);
        // 如果用户权限低，对查询到的公告进行脱敏
        Long currentId = BaseContext.getCurrentId();
        if (userService.getById(currentId).getRole() <= DEFAULT_USER) {
            list = list.stream().map(announcement -> announcementService.getSafetyAnnouncement(announcement)).collect(Collectors.toList());
        }

        return ResultUtils.success(list);
    }

    /**
     * 根据ID删除公告
     *
     * @param id 需要删除的公告id
     * @return 被删除的公告id
     */
    @PostMapping("/delete")
    @Operation(description = "删除公告")  // 接口信息描述
    public BaseResponse<Long> deleteAnnouncement(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }
        // 获取当前登录用户
        Long currentId = BaseContext.getCurrentId();
        User user = userService.getById(currentId);
        // 鉴权
        if (user.getRole() <= DEFAULT_USER) {
            throw new BusinessException(ErrorCode.NO_AUTH, "用户权限不足");
        }
        // 尝试删除公告
        boolean result = announcementService.removeById(id);
        // 删除失败
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除公告失败");
        }
        // 删除成功
        return ResultUtils.success(id);
    }

    /**
     * 更新/修改公告
     *
     * @param announcement 要修改的原公告
     * @return 修改后的新公告
     */
    @PostMapping("/update")
    @Operation(description = "更新/修改公告")  // 接口信息描述
    public BaseResponse<Announcement> updateAnnouncement(@RequestBody Announcement announcement) {
        // 获取当前登录用户
        Long currentUserId = BaseContext.getCurrentId();
        User currentUser = userService.getById(currentUserId);

        // 鉴权
        if (currentUser.getRole() <= DEFAULT_USER) {
            throw new BusinessException(ErrorCode.NO_AUTH, "权限不足");
        }
        // 权限足够，可以修改
        boolean result = announcementService.updateById(announcement);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "修改失败");
        }
        return ResultUtils.success(announcementService.getById(announcement.getId()));
    }
}
