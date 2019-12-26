package com.wenjun.seckill.config;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Component;

/**
 * Redis配置类，可以在此修改Redis默认配置
 * @Author: wenjun
 * @Date: 2019/12/26 12:04
 */
@Component
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
public class RedisConfig {
}
