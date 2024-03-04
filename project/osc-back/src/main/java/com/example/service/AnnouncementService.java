package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.dto.AnnouncementAddDTO;
import com.example.model.entity.Announcement;

import java.util.List;

/**
 * 针对表【announcement】的数据库操作Service
 *
 * @author lwy
 */
public interface AnnouncementService extends IService<Announcement> {

    /**
     * 添加公告
     *
     * @param announcementAddDTO 添加公告请求体
     * @return 公告ID
     */
    Long addAnnouncement(AnnouncementAddDTO announcementAddDTO);

    /**
     * 删除公告
     *
     * @param id 公告id
     */
    void deleteAnnouncement(Long id);

    /**
     * 更新公告
     *
     * @param announcement 新公告
     */
    void updateAnnouncement(Announcement announcement);

    /**
     * 公告脱敏
     *
     * @param announcement 脱敏前的公告
     * @return 脱敏后的公告
     */
    Announcement getSafetyAnnouncement(Announcement announcement);

    /**
     * 查询公告
     *
     * @param title 公告标题
     * @return 公告列表
     */
    List<Announcement> listAnnouncements(String title);
}
