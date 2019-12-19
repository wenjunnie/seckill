package com.wenjun.seckill.service.model;

import lombok.Data;

/**
 * @Author: wenjun
 * @Date: 2019/12/19 14:01
 */
@Data
public class UserModel  {
    private Integer id;
    private String name;
    private Byte gender;
    private Integer age;
    private String telphone;
    private String registerMode;
    private String thirdPartyId;

    private String encrptPassword;
}
