package com.linfengda.sb.support.redis.cache.handler.strategy.impl;

import com.linfengda.sb.support.redis.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.redis.cache.handler.strategy.AbstractCacheStrategy;
import com.linfengda.sb.support.redis.config.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 描述: list
 *
 * @author: linfengda
 * @date: 2020-07-08 16:22
 */
@Slf4j
public class ListCacheStrategy extends AbstractCacheStrategy {

    @Override
    public Object doGetCache(CacheParamDTO param) {
        // 获取redis列表，如：myList:{id}
        Object value = getSimpleRedisTemplate().listGetAll(param.getKey());
        return value;
    }

    @Override
    public void doSetCache(CacheParamDTO param, Object value) {
        if (value instanceof List) {
            List list = (List) value;
            getSimpleRedisTemplate().listAddAll(param.getKey(), list, param.getTimeOutMillis(), TimeUnit.MILLISECONDS);
        }else if (value instanceof Set) {
            Set set = (Set) value;
            getSimpleRedisTemplate().listAddAll(param.getKey(), set, param.getTimeOutMillis(), TimeUnit.MILLISECONDS);
        }else {
            getSimpleRedisTemplate().listAdd(param.getKey(), value, param.getTimeOutMillis(), TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public Boolean hasKey(CacheParamDTO param) {
        return null;
    }

    @Override
    public Long getCurrentCacheSize(CacheParamDTO param) {
        // 获取指定类型redis列表数量大小，如：myList:{*}大小
        String keyPattern = param.getPrefix() + Constant.ASTERISK;
        Set<String> set = getSimpleRedisTemplate().keys(keyPattern);
        if (CollectionUtils.isEmpty(set)) {
            return 0L;
        }
        return (long) set.size();
    }
}
