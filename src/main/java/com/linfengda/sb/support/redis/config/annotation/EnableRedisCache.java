package com.linfengda.sb.support.redis.config.annotation;

import com.linfengda.sb.support.redis.cache.manager.AopOrderManager;
import com.linfengda.sb.support.redis.config.selector.RedisCacheConfigSelector;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 描述: 开启redis缓存注解，自动引入redisTemplate，redisDistributedLock，redis注解框架
 *
 * @author linfengda
 * @create 2020-03-24 17:56
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RedisCacheConfigSelector.class})
public @interface EnableRedisCache {
    /**
     * true     目标对象实现了接口	使用CGLIB代理机制
     * true     目标对象没有接口(只有实现类)	使用CGLIB代理机制
     * false    目标对象实现了接口	使用JDK动态代理机制(代理所有实现了的接口)
     * false	目标对象没有接口(只有实现类)	使用CGLIB代理机制
     * @return
     */
    boolean proxyTargetClass() default false;
    /**
     * 代理模式：JDK/AspectJ
     * @return
     */
    AdviceMode mode() default AdviceMode.PROXY;
    /**
     * 查询注解aop优先级
     * @return
     */
    int queryOrder() default AopOrderManager.QUERY_CACHE;
    /**
     * 删除注解aop优先级
     * @return
     */
    int deleteOrder() default AopOrderManager.DELETE_CACHE;
    /**
     * 更新注解aop优先级
     * @return
     */
    int updateOrder() default AopOrderManager.UPDATE_CACHE;
}
