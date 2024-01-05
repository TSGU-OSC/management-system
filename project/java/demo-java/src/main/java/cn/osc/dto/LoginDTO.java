package cn.osc.dto;

import lombok.Data;

/**
 * @Author: fsq
 * @Date: 2024/1/3
 * @Description:
 **/
@Data
public class LoginDTO {
    private String username;
    private String password;
    /**
     * 验证码
     */
    private String code;
    /**
     * uuid
     */
    private String uuid="";

}
