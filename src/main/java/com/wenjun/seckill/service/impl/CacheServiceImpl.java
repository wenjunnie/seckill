package com.wenjun.seckill.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wenjun.seckill.service.CacheService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wenjun
 * @Date: 2019/12/27 14:54
 */
@Service
public class CacheServiceImpl implements CacheService {

    private Cache<String,Object> commonCache = null;

    @PostConstruct
    public void init() {
        commonCache = CacheBuilder.newBuilder()
                //设置缓存容器初始容量为10
                .initialCapacity(10)
                //最大可以存储100个KEY，超过后按LRU策略移除
                .maximumSize(100)
                //设置写缓存后60秒过期（相对时间）
                .expireAfterWrite(60,TimeUnit.SECONDS).build();
    }

    @Override
    public void setCommonCache(String key, Object value) {
        commonCache.put(key,value);
    }

    @Override
    public Object getFromCommonCache(String key) {
        return commonCache.getIfPresent(key);
    }
}
