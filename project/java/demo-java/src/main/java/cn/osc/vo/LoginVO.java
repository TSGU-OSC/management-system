package cn.osc.vo;

import cn.osc.entity.User;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: fsq
 * @Date: 2024/1/18
 * @Description: 登录VO
 **/
@Data
public class LoginVO implements Serializable {
    private Integer id;
    private String token;
    private User user;



}
