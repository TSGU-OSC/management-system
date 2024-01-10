package cn.osc.dto;

import lombok.Data;

/**
 * @Author fsq
 * @Date 2024/1/6
 * @Description 查询业务实体
 * 这里仅仅定义了三个参数，在实际应用中可以定义多个参数
 */
@Data
public class QueryDTO {
    private Integer pageNo;    //页码
    private Integer pageSize;  //页面大小
    //关键字
    private UserDTO userDTO;
//    private String username;
//    private String gender;
//    private String code;
//    private String clazz;
//    private String major;
//    private String academy;
//    private String duty;


}
