package com.example.controller;

import com.example.common.ResultUtils;
import com.example.model.dto.AnnouncementAddDTO;
import com.example.model.entity.Announcement;
import com.example.model.vo.ResponseVO;
import com.example.service.AnnouncementService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公告服务控制类
 *
 * @author lwy
 */
@ApiSupport(author = "lwy") // 接口作者
@Tag(name = "公告管理接口")   // 接口分组名
@Slf4j  // 日志
@RestController
// @CrossOrigin  // 支持跨域
@RequestMapping("/announce")
public class AnnouncementController {

    @Resource
    AnnouncementService announcementService;

    /**
     * 添加公告
     *
     * @return 新公告id
     */
    @PostMapping("/add")
    @Operation(description = "添加新公告")  // 接口信息描述
    public ResponseVO<Long> addAnnouncement(@RequestBody @Validated AnnouncementAddDTO announcementAddDTO) {
        long newAnnouncementId = announcementService.addAnnouncement(announcementAddDTO);
        return ResultUtils.success(newAnnouncementId);
    }

    /**
     * 查询公告
     *
     * @return 公告列表
     */
    @GetMapping("/search")
    @Operation(description = "根据标题查询公告信息")  // 接口信息描述
    public ResponseVO<List<Announcement>> listAnnouncements(String title) {
        List<Announcement> announcementList = announcementService.listAnnouncements(title);

        return ResultUtils.success(announcementList);
    }

    /**
     * 根据ID删除公告
     *
     * @param id 需要删除的公告id
     * @return 被删除的公告id
     */
    @PostMapping("/delete")
    @Operation(description = "删除公告")  // 接口信息描述
    public ResponseVO<Long> deleteAnnouncement(@NotNull Long id) {
        announcementService.deleteAnnouncement(id);
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
    public ResponseVO<Announcement> updateAnnouncement(@RequestBody Announcement announcement) {
        announcementService.updateAnnouncement(announcement);
        return ResultUtils.success(announcementService.getById(announcement.getId()));
    }
}
