package com.wenjun.seckill.controller;

import com.aliyuncs.exceptions.ClientException;
import com.wenjun.seckill.controller.viewobject.UserVO;
import com.wenjun.seckill.enums.EmBusinessError;
import com.wenjun.seckill.error.BusinessException;
import com.wenjun.seckill.response.CommonReturnType;
import com.wenjun.seckill.service.impl.UserServiceImpl;
import com.wenjun.seckill.service.model.UserModel;
import com.wenjun.seckill.util.AliyunMessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: wenjun
 * @Date: 2019/12/19 12:25
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(allowedHeaders = "*",allowCredentials = "true")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //通过ID获取对应用户
    @GetMapping(value = "/get")
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        // 调用service服务获取对应ID的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);
        if (userModel == null) throw new BusinessException(EmBusinessError.USER_NOT_EXIT);
        // 将userModel转化为UserVO（不让前端看到密码等信息）
        UserVO userVO = convertFromModel(userModel);
        return CommonReturnType.create(userVO);
    }

    //用户获取OTP短信接口
    @GetMapping(value = "/getotp")
    public CommonReturnType getOtp(@RequestParam(name = "telphone") String telphone) throws ClientException {
        //生成otp验证码
//        Random random = new Random();
//        int randomInt = random.nextInt(99999);
//        randomInt += 10000;
//        String otpCode = String.valueOf(randomInt);
        String otpCode = AliyunMessageUtil.createRandomNum(6);
        //将otp验证码与手机号关联（通过session）
        httpServletRequest.getSession().setAttribute("telphone",telphone);
        httpServletRequest.getSession().setAttribute("otpCode",otpCode);
        //将otp验证码通过短信发送给用户，省略
        AliyunMessageUtil.sendMsg(telphone,otpCode);
        System.out.println("telphone= " + telphone + " otpCode= " + otpCode);
        return CommonReturnType.create("验证码发送成功");
    }

    //用户注册接口
    @PostMapping(value = "/register")
    public CommonReturnType register(@RequestParam(name = "telphone") String telphone,
                                     @RequestParam(name = "otpCode") String otpCode,
                                     @RequestParam(name = "name") String name,
                                     @RequestParam(name = "gender") Byte gender,
                                     @RequestParam(name = "age") Integer age,
                                     @RequestParam(name = "password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        String inSessionOtpCode = (String) httpServletRequest.getSession().getAttribute("otpCode");
        if (!StringUtils.equals(otpCode,inSessionOtpCode)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
        }
        //用户注册
        UserModel userModel = new UserModel();
        userModel.setTelphone(telphone);
        userModel.setName(name);
        userModel.setGender(gender);
        userModel.setAge(age);
        userModel.setRegisterMode("byPhone");
        if (!password.isEmpty()) {//不能用==null,"" != null
            userModel.setEncrptPassword(this.EncodeByMd5(password));
        }

        userService.register(userModel);
        return CommonReturnType.create("注册成功");
    }

    //用户登录接口
    @GetMapping(value = "/login")
    public CommonReturnType login(@RequestParam(name = "telphone") String telphone,
                                  @RequestParam(name = "password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(telphone) || StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //校验登录是否合法
        UserModel userModel = userService.validateLogin(telphone,password);
        //将登录凭证加入到用户登录的session中
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
        return CommonReturnType.create("登录成功");
    }

    public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //加密字符串
        String newstr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }

    private UserVO convertFromModel(UserModel userModel) {
        if (userModel == null) return null;
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO );
        return userVO;
    }
}
