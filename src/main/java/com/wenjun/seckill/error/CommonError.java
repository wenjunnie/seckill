package com.wenjun.seckill.error;

/**
 * @Author: wenjun
 * @Date: 2019/12/19 15:25
 */
public interface CommonError {
    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);
}
