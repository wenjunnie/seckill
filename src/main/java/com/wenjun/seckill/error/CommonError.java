package com.wenjun.seckill.error;

/**
 * @Author: wenjun
 * @Date: 2019/12/19 15:25
 */
public interface CommonError {
    int getErrCode();
    String getErrMsg();
    CommonError setErrMsg(String errMsg);
}
