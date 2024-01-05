package cn.osc.service;

import cn.osc.dto.LoginDTO;
import cn.osc.entity.User;
import cn.osc.result.Result;

/**
 * @Author: fsq
 * @Date: 2024/1/3
 * @Description:
 **/

public interface LoginService {
    public Result login(String username, String password, String code, String uuid);

    Result register(User user);
}
