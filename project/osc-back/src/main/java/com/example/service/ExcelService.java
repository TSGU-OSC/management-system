package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.dto.QueryDTO;
import com.example.model.entity.ExcelInput;
import com.example.model.entity.User;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;


/**
 * Excel服务
 *
 * @author lwy
 */
public interface ExcelService extends IService<User> {

    /**
     * 导入用户信息
     */
    void excelInput(List<ExcelInput> cachedDataList);

    /**
     * 导出用户信息
     */
    void excelOutput(HttpServletResponse response);

}
