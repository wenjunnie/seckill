package com.wenjun.seckill.service;

import com.wenjun.seckill.error.BusinessException;
import com.wenjun.seckill.service.model.UserModel;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: wenjun
 * @Date: 2019/12/19 12:28
 */
public interface UserService {
    //通过用户ID获取用户对象的方法
    UserModel getUserById(Integer id);
    //通过缓存获取用户对象
    UserModel getUserByIdInCache(Integer id);
    void register(UserModel userModel) throws BusinessException;
    //判断登录是否合法
    UserModel validateLogin(String telphone, String password) throws BusinessException;
    //校验用户是否已注册
    UserModel getUserByTelphone(String telphone) throws BusinessException;
    //修改密码
    boolean changePassword(UserModel userModel);
}
