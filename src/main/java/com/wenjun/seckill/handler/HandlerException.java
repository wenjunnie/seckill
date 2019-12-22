package com.wenjun.seckill.handler;

import com.wenjun.seckill.enums.EmBusinessError;
import com.wenjun.seckill.error.BusinessException;
import com.wenjun.seckill.response.CommonReturnType;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wenjun
 * @Date: 2019/12/19 15:48
 */
@ControllerAdvice
public class HandlerException {
    @ExceptionHandler(Exception.class)
    @ResponseBody//不要忘了加
    public Object handler(HttpServletRequest request, HttpServletResponse httpServletResponse, Exception e) {
        e.printStackTrace();//在控制台打印异常信息
        Map<String, Object> map = new HashMap<>();
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            map.put("errCode", businessException.getErrCode());
            map.put("errMsg", businessException.getErrMsg());
        } else if (e instanceof ServletRequestBindingException) {//传参错误（405）
            map.put("errCode", EmBusinessError.UNKNOWN_ERROR);
            map.put("errMsg", "url路由绑定问题");
        } else if (e instanceof NoHandlerFoundException) {//页面404
            map.put("errCode", EmBusinessError.UNKNOWN_ERROR);
            map.put("errMsg", "没有找到对应的访问路径");
        } else {
            map.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
            map.put("errMsg", EmBusinessError.UNKNOWN_ERROR.getErrMsg());
        }
        return CommonReturnType.create(map,"fail");
    }
}
