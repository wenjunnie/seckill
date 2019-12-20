package com.wenjun.seckill.service.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: wenjun
 * @Date: 2019/12/19 14:01
 */
@Data
public class UserModel  {
    private Integer id;
    @NotBlank(message = "姓名不能为空")
    private String name;
    @NotNull(message = "性别不能不填")
    private Byte gender;
    @NotNull(message = "年龄不能不填")
    @Min(value = 0,message = "年龄必须大于0")
    @Max(value = 100,message = "年龄必须小于100")
    private Integer age;
    @NotBlank(message = "手机号不能为空")
    private String telphone;
    private String registerMode;
    private String thirdPartyId;
    @NotBlank(message = "密码不能为空")
    private String encrptPassword;
}
