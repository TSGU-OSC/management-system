package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.ErrorCode;
import com.example.exception.BusinessException;
import com.example.mapper.AnnouncementMapper;
import com.example.model.entity.Announcement;
import com.example.model.vo.AnnouncementAddVO;
import com.example.service.AnnouncementService;
import org.springframework.stereotype.Service;

/**
 * 针对表【announcement】的数据库操作Service实现
 *
 * @author lwy
 */
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement>
        implements AnnouncementService {

    /**
     * 添加公告
     *
     * @param announcementAddVO 添加公告请求体
     * @return 公告ID
     */
    @Override
    public long addAnnouncement(AnnouncementAddVO announcementAddVO) {
        if (announcementAddVO == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "参数为空");
        }
        Announcement announcement = new Announcement();
        announcement.setTitle(announcementAddVO.getTitle());
        announcement.setContent(announcementAddVO.getContent());
        // 尝试存储公告
        boolean save = this.save(announcement);
        // 存储公告失败
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "公告存储失败");
        }
        return announcement.getId();
    }

    /**
     * 公告脱敏
     *
     * @param announcement 脱敏前的公告
     * @return 脱敏后的公告
     */
    @Override
    public Announcement getSafetyAnnouncement(Announcement announcement) {
        Announcement newAnnouncement = new Announcement();
        newAnnouncement.setId(announcement.getId());
        newAnnouncement.setTitle(announcement.getTitle());
        newAnnouncement.setContent(announcement.getContent());
        newAnnouncement.setUpdateTime(announcement.getUpdateTime());

        return newAnnouncement;
    }
}




