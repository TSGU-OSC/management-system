package com.example.mapper;

import com.example.model.domain.NewUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author lenovo
* @description 针对表【new_user】的数据库操作Mapper
* @createDate 2024-01-23 13:07:09
* @Entity com.example.model.domain.NewUser
*/
@Mapper
public interface NewUserMapper extends BaseMapper<NewUser> {

}




