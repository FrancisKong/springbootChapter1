
package com.linfengda.sb.support.cache.builder;

import com.linfengda.sb.support.cache.config.Constant;
import com.linfengda.sb.support.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.cache.entity.meta.CacheMethodMeta;
import com.linfengda.sb.support.cache.entity.type.DataType;
import com.linfengda.sb.support.cache.entity.type.KeyType;
import com.linfengda.sb.support.cache.exception.BusinessException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 缓存key封装
 *
 * @author: linfengda
 * @date: 2020-07-06 15:12
 */
public enum CacheParamBuilder {
    /**
     * builder
     */
    INSTANCE;

    /**
     * 初始化缓存参数DTO
     * @param cacheMethodMeta   方法缓存信息
     * @param arguments         方法参数列表
     */
    public CacheParamDTO initCacheParam(CacheMethodMeta cacheMethodMeta, Object[] arguments) {
        // 初始化缓存前缀，过期策略
        CacheParamDTO cacheParamDTO = new CacheParamDTO();
        cacheParamDTO.setDataType(cacheMethodMeta.getDataType());
        cacheParamDTO.setPrefix(StringUtils.isEmpty(cacheMethodMeta.getPrefix()) ? cacheMethodMeta.getMethodName() : cacheMethodMeta.getPrefix());
        cacheParamDTO.setTimeOut(cacheMethodMeta.getTimeOut());
        cacheParamDTO.setTimeUnit(cacheMethodMeta.getTimeUnit());
        cacheParamDTO.setStrategies(cacheMethodMeta.getStrategies());
        cacheParamDTO.setMaxSize(cacheMethodMeta.getMaxSize());
        cacheParamDTO.setAllEntries(cacheMethodMeta.getAllEntries());
        // 初始化缓存key
        List<String> keys = getKeys(cacheMethodMeta.getKeyMetas(), arguments);
        if (DataType.OBJECT == cacheMethodMeta.getDataType()) {
            cacheParamDTO.setKey(buildObjectKey(cacheParamDTO.getPrefix(), keys));
        }
        if (DataType.LIST == cacheMethodMeta.getDataType()) {
            cacheParamDTO.setKey(buildObjectKey(cacheParamDTO.getPrefix(), keys));
        }
        if (DataType.SET == cacheMethodMeta.getDataType()) {
            cacheParamDTO.setKey(buildObjectKey(cacheParamDTO.getPrefix(), keys));
        }
        if (DataType.HASH == cacheMethodMeta.getDataType()) {
            cacheParamDTO.setHashKey(buildHashKey(cacheParamDTO.getPrefix(), keys));
        }
        cacheParamDTO.setLruKey(buildLruKey(cacheParamDTO));
        cacheParamDTO.setLockKey(buildLockKey(cacheParamDTO));
        return cacheParamDTO;
    }

    /**
     * 初始化缓存key列表
     * @param keyMetas  缓存方法key列表
     * @param arguments 缓存方法参数列表
     * @return          缓存key列表
     */
    private List<String> getKeys(List<CacheMethodMeta.CacheKeyMeta> keyMetas, Object[] arguments) {
        List<String> keys = new ArrayList<>();
        if (CollectionUtils.isEmpty(keyMetas)) {
            return keys;
        }
        if (null == arguments || 0 == arguments.length) {
            return keys;
        }
        for (CacheMethodMeta.CacheKeyMeta keyMeta : keyMetas) {
            Object argument = arguments[keyMeta.getIndex()];
            if (!KeyType.isBaseType(argument.getClass().getName())) {
                throw new BusinessException("不支持的缓存key参数类型：" + argument.getClass().getName());
            }
            if (null == argument) {
                keys.add(keyMeta.getNullKey());
                continue;
            }
            keys.add(String.valueOf(argument));
        }
        return keys;
    }

    /**
     * 获取object类型缓存key
     * @param prefix    key前缀
     * @param keys      key列表
     * @return          缓存key
     */
    private String buildObjectKey(String prefix, List<String> keys) {
        StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        if (CollectionUtils.isEmpty(keys)) {
            return builder.toString();
        }
        for (String key : keys) {
            builder.append(Constant.COLON + key);
        }
        return builder.toString();
    }

    /**
     * 获取hash类型缓存key
     * @param prefix    key前缀
     * @param keys      key列表
     * @return          缓存key
     */
    private HashKey buildHashKey(String prefix, List<String> keys) {
        HashKey hashKey = new HashKey();
        hashKey.setKey(prefix);
        if (CollectionUtils.isEmpty(keys)) {
            return hashKey;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if (i == 0) {
                builder.append(key);
            }else {
                builder.append(Constant.COLON + key);
            }
        }
        hashKey.setHashKey(builder.toString());
        return hashKey;
    }

    /**
     * 获取LRU记录缓存key
     * @param param 缓存参数
     * @return      缓存key
     */
    public String buildLruKey(CacheParamDTO param) {
        if (DataType.OBJECT == param.getDataType() || DataType.HASH == param.getDataType()) {
            return Constant.LRU_RECORD_PREFIX + Constant.COLON + param.getPrefix();
        }
        if (DataType.LIST == param.getDataType() || DataType.SET == param.getDataType()) {
            return Constant.LRU_RECORD_PREFIX + Constant.COLON + param.getKey();
        }
        throw new BusinessException("不支持的缓存数据类型！");
    }

    /**
     * 获取缓存lockKey
     * @param param 缓存参数
     * @return      缓存lockKey
     */
    public String buildLockKey(CacheParamDTO param) {
        return Constant.LOCK_PREFIX + Constant.COLON + param.getKey();
    }
}