package cn.osc.mapper;

import cn.osc.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: fsq
 * @Date: 2024/1/3
 * @Description: TODO
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    IPage<User> selectUserPage(Page<User> page,String keyword);
}
