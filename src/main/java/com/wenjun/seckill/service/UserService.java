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
    void register(UserModel userModel) throws BusinessException;
    UserModel validateLogin(String telphone, String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException;
}
