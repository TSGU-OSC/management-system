package com.example.service;

import com.example.model.entity.Announcement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.dto.AnnouncementAddDTO;

import java.util.List;

/**
 * 针对表【announcement】的数据库操作Service
 *
 * @author lwy
 */
public interface AnnouncementService extends IService<Announcement> {

    Long addAnnouncement(AnnouncementAddDTO announcementAddDTO);

    Announcement getSafetyAnnouncement(Announcement announcement);

    List<Announcement> listAnnouncements(String title);
}
