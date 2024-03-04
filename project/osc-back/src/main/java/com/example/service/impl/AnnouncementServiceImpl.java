package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.BaseContext;
import com.example.enums.ErrorCodeEnum;
import com.example.exception.BusinessException;
import com.example.mapper.AnnouncementMapper;
import com.example.model.dto.AnnouncementAddDTO;
import com.example.model.entity.Announcement;
import com.example.model.entity.User;
import com.example.service.AnnouncementService;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.constant.UserConstant.DEFAULT_USER;

/**
 * 针对表【announcement】的数据库操作Service实现
 *
 * @author lwy
 */
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement>
        implements AnnouncementService {

    @Resource
    UserService userService;

    /**
     * 添加公告
     *
     * @param announcementAddDTO 添加公告请求体
     * @return 公告ID
     */
    @Override
    public Long addAnnouncement(AnnouncementAddDTO announcementAddDTO) {
        // 获取当前线程用户
        Long currentId = BaseContext.getCurrentId();
        User currentUser = userService.getById(currentId);
        // 鉴权
        if (currentUser.getRole() <= DEFAULT_USER) {
            throw new BusinessException(ErrorCodeEnum.NO_AUTH, "权限不足");
        }
        Announcement announcement = new Announcement();
        announcement.setTitle(announcementAddDTO.getTitle());
        announcement.setContent(announcementAddDTO.getContent());
        // 存储公告
        this.save(announcement);
        return announcement.getId();
    }

    /**
     * 删除公告
     *
     * @param id 公告id
     */
    @Override
    public void deleteAnnouncement(Long id) {
        // 获取当前登录用户
        Long currentId = BaseContext.getCurrentId();
        User user = userService.getById(currentId);
        // 鉴权
        if (user.getRole() <= DEFAULT_USER) {
            throw new BusinessException(ErrorCodeEnum.NO_AUTH, "用户权限不足");
        }
        // 删除公告
        this.removeById(id);
    }

    /**
     * 更新公告
     *
     * @param announcement 新公告
     */
    @Override
    public void updateAnnouncement(Announcement announcement) {
        // 获取当前登录用户
        Long currentUserId = BaseContext.getCurrentId();
        User currentUser = userService.getById(currentUserId);

        // 鉴权
        if (currentUser.getRole() <= DEFAULT_USER) {
            throw new BusinessException(ErrorCodeEnum.NO_AUTH, "权限不足");
        }
        // 权限足够，可以修改
        this.updateById(announcement);
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

    /**
     * 查询公告
     *
     * @param title 公告标题
     * @return 公告列表
     */
    @Override
    public List<Announcement> listAnnouncements(String title) {
        // 模糊匹配
        LambdaQueryWrapper<Announcement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Announcement::getTitle, title);

        List<Announcement> list = this.list(queryWrapper);
        // 如果用户权限低，对查询到的公告进行脱敏
        Long currentId = BaseContext.getCurrentId();
        if (userService.getById(currentId).getRole() <= DEFAULT_USER) {
            list = list.stream().map(this::getSafetyAnnouncement).collect(Collectors.toList());
        }
        return list;
    }
}




