package com.linfengda.sb.support.cache.annotation;

import com.linfengda.sb.support.cache.entity.type.DataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 查询缓存注解
 *
 * @author linfengda
 * @create 2020-03-26 10:34
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryCache {
    /**
     * 缓存前缀，建议使用方法名
     */
    String prefix() default "";
    /**
     * 缓存失效时间
     */
    long timeOut() default 60L;
    /**
     * 缓存失效时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
    /**
     * 缓存类型
     */
    DataType type() default DataType.OBJECT;
}