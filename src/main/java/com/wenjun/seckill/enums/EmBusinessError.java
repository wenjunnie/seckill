package com.wenjun.seckill.enums;

import com.wenjun.seckill.error.CommonError;

/**
 * @Author: wenjun
 * @Date: 2019/12/19 15:28
 */
public enum EmBusinessError implements CommonError {
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),
    USER_NOT_EXIT(20001,"用户不存在"),
    USER_LOGIN_FAIL(20002,"用户或密码不正确"),
    USER_NOT_LOGIN(20003,"用户还未登录"),
    STOCK_NOT_ENOUGH(20002,"库存不足"),
    ;

    private int errCode;
    private String errMsg;

    EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
