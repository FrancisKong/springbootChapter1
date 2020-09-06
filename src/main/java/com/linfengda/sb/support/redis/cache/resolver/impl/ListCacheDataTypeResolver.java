package com.linfengda.sb.support.redis.cache.resolver.impl;

import com.linfengda.sb.support.redis.Constant;
import com.linfengda.sb.support.redis.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.redis.cache.entity.type.DataType;
import com.linfengda.sb.support.redis.cache.resolver.AbstractCacheDataTypeResolver;
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
public class ListCacheDataTypeResolver extends AbstractCacheDataTypeResolver {

    @Override
    public boolean support(DataType dataType) {
        return DataType.LIST == dataType;
    }

    @Override
    public Object doGetCache(CacheParamDTO param) {
        // 获取redis列表，如：myList:{id}
        Object value = genericRedisTemplate.listGetAll(param.getKey());
        return value;
    }

    @Override
    public void doSetCache(CacheParamDTO param, Object value) {
        if (value instanceof List) {
            List list = (List) value;
            genericRedisTemplate.listAddAll(param.getKey(), list, param.getTimeOutMillis(), TimeUnit.MILLISECONDS);
        }else if (value instanceof Set) {
            Set set = (Set) value;
            genericRedisTemplate.listAddAll(param.getKey(), set, param.getTimeOutMillis(), TimeUnit.MILLISECONDS);
        }else {
            genericRedisTemplate.listAdd(param.getKey(), value, param.getTimeOutMillis(), TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void delCache(CacheParamDTO param) {
        Boolean allEntries = param.getAllEntries();
        if (Boolean.TRUE.equals(allEntries)) {
            delAllEntries(param);
            return;
        }
        genericRedisTemplate.delete(param.getKey());
    }

    @Override
    public Boolean hasKey(CacheParamDTO param) {
        return null;
    }

    @Override
    public Long getCurrentCacheSize(CacheParamDTO param) {
        // 获取指定类型redis列表数量，如：myList:{*}大小
        String keyPattern = param.getPrefix() + Constant.ASTERISK;
        Set<String> set = genericRedisTemplate.keys(keyPattern);
        if (CollectionUtils.isEmpty(set)) {
            return 0L;
        }
        return (long) set.size();
    }
}
