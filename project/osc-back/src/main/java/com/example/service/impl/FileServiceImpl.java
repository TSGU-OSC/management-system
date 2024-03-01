package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.config.MinIOProperty;
import com.example.mapper.UserMapper;
import com.example.model.entity.User;
import com.example.service.FileService;
import com.example.service.UserService;
import com.example.utils.MinioUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 文件服务实现类
 *
 * @author lwy
 */
@Service
public class FileServiceImpl extends ServiceImpl<UserMapper, User> implements FileService {
    @Resource
    MinIOProperty minioProperty = new MinIOProperty();
    @Resource
    MinioUtil minioUtil = new MinioUtil();
    @Resource
    UserService userService;

    /**
     * 上传文件
     *
     * @param file 文件
     * @param id   用户id
     * @return 文件名
     */
    @Override
    public String uploadFile(MultipartFile file, Long id) {
        String fileName = minioUtil.minioUpload(file, minioProperty.getBucket());
        // 同时删除原头像文件
        User user = userService.getById(id);
        if(user.getAvator()!=null) {
            deleteFile(user.getAvator(),user);
        }
        // 同时将头像文件名存储到数据库中
        user.setAvator(fileName);
        userService.updateUser(user);
        return fileName;
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名
     * @param response HttpServletResponse
     */
    @Override
    public void downloadFile(String fileName, HttpServletResponse response) {
        minioUtil.downloadFile(minioProperty.getBucket(), fileName, response);
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名
     * @param user     用户信息
     */
    @Override
    public void deleteFile(String fileName, User user) {
        minioUtil.deleteFile(minioProperty.getBucket(), fileName);
        // 同时删除数据库中的用户头像
        user.setAvator(null);
        userService.updateUser(user);
    }
}
