package cn.osc.service;

import cn.osc.dto.CountUserDTO;
import cn.osc.dto.QueryDTO;
import cn.osc.dto.UserDTO;
import cn.osc.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @Author fsq
 * @Date 2024/1/6
 * @Description
 */
public interface UserService {

    /**
     * 分页查询
     * @param queryDTO
     * @return
     */
    IPage<User> selectUserPage(QueryDTO queryDTO);

    Integer addUser(User user);

    Integer updateUser(User user);

    Integer deleteUser(Integer id);

    void batchDelete(List<Integer> ids);

    CountUserDTO count();
}
