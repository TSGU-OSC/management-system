package com.example.service;

import com.example.model.entity.Announcement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.vo.AnnouncementAddVO;

/**
 * 针对表【announcement】的数据库操作Service
 *
 * @author lwy
 */
public interface AnnouncementService extends IService<Announcement> {

    long addAnnouncement(AnnouncementAddVO announcementAddVO);

    Announcement getSafetyAnnouncement(Announcement announcement);
}
