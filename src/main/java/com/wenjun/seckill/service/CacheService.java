package com.wenjun.seckill.service;

/**
 * 封装本地缓存操作类
 * @Author: wenjun
 * @Date: 2019/12/27 14:52
 */
public interface CacheService {
    //存方法
    void setCommonCache(String key,Object value);
    //取方法
    Object getFromCommonCache(String key);
}
