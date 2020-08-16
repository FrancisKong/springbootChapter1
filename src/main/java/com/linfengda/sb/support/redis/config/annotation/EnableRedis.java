package com.linfengda.sb.support.redis.config.annotation;

import com.linfengda.sb.support.redis.config.selector.RedisConfigSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @description: 开启redis，自动引入redisTemplate，redisDistributedLock
 * @author: linfengda
 * @date: 2020-07-26 22:34
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RedisConfigSelector.class})
public @interface EnableRedis {
}