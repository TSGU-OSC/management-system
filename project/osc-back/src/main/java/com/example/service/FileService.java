package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务类
 *
 * @author lwy
 */
public interface FileService extends IService<User> {
    /**
     * 上传文件
     *
     * @param file 文件
     * @param id   用户id
     * @return 文件名
     */
    String uploadFile(MultipartFile file, Long id);

    /**
     * 下载文件
     *
     * @param fileName 文件名
     * @param response HttpServletResponse
     */
    void downloadFile(String fileName, HttpServletResponse response);

    /**
     * 删除文件
     *
     * @param fileName 文件名
     * @param user     用户信息
     */
    void deleteFile(String fileName, User user);
}
