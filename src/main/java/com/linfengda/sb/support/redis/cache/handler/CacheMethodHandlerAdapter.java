package com.linfengda.sb.support.redis.cache.handler;

import com.linfengda.sb.support.redis.cache.builder.CacheMethodMetaBuilder;
import com.linfengda.sb.support.redis.cache.builder.CacheParamBuilder;
import com.linfengda.sb.support.redis.cache.entity.dto.CacheParamDTO;
import com.linfengda.sb.support.redis.cache.entity.dto.CacheTargetDTO;
import com.linfengda.sb.support.redis.cache.entity.meta.CacheMethodMeta;
import com.linfengda.sb.support.redis.cache.entity.type.CacheAnnotationType;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * 描述: 提供对
 * {@link com.linfengda.sb.support.redis.cache.annotation.QueryCache},
 * {@link com.linfengda.sb.support.redis.cache.annotation.DeleteCache},
 * {@link com.linfengda.sb.support.redis.cache.annotation.UpdateCache}
 * 注解的支持
 *
 * @author linfengda
 * @create 2019-12-19 17:52
 */
@Slf4j
public class CacheMethodHandlerAdapter {

    /**
     * Actually invoke the handler
     * @param invocation
     * @param annotationType
     * @return
     * @throws Throwable
     */
    protected Object invokeCacheMethod(MethodInvocation invocation, CacheAnnotationType annotationType) throws Throwable {
        Method method = invocation.getMethod();
        Object[] arguments = invocation.getArguments();
        CacheMethodMeta cacheMethodMeta = CacheMethodMetaBuilder.getCacheMethodMeta(method);
        CacheParamDTO param = CacheParamBuilder.INSTANCE.initCacheParam(cacheMethodMeta, arguments);
        CacheTargetDTO cacheTargetDTO = new CacheTargetDTO();
        cacheTargetDTO.setInvocation(invocation);
        cacheTargetDTO.setParam(param);
        CacheHandler handler = CacheHandlerHolder.INSTANCE.getHandler(annotationType);
        return handler.doCache(cacheTargetDTO);
    }
}
