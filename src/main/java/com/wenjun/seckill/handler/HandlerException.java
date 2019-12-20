package com.wenjun.seckill.handler;

import com.wenjun.seckill.enums.EmBusinessError;
import com.wenjun.seckill.error.BusinessException;
import com.wenjun.seckill.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wenjun
 * @Date: 2019/12/19 15:48
 */
@ControllerAdvice
public class HandlerException {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody//不要忘了加
    public Object handler(HttpServletRequest request, Exception e) {
        Map<String, Object> map = new HashMap<>();
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            map.put("errCode", businessException.getErrCode());
            map.put("errMsg", businessException.getErrMsg());
        } else {
            map.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
            map.put("errMsg", EmBusinessError.UNKNOWN_ERROR.getErrMsg());
        }
        return CommonReturnType.create(map,"fail");
    }
}
