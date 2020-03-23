package com.linfengda.sb.support.middleware.redis.lock;

import com.linfengda.sb.chapter1.common.util.ServerRunTimeUtil;
import com.linfengda.sb.support.middleware.redis.template.SimpleRedisTemplate;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 描述: Redis分布式锁
 *
 * @author linfengda
 * @create 2020-03-23 17:33
 */
@Slf4j
public class DistributeRedisLock {
    /**
     * Redis分布式锁默认超时时间
     */
    private final int DEFAULT_LOCK_EXPIRE_TIME = 60;

    @Setter
    @Resource
    private SimpleRedisTemplate simpleRedisTemplate;

    protected boolean tryLock(String[] keys) {
        Map map = new HashMap();
        for (String key : keys) {
            map.put(key, getCurrentThreadId());
        }
        Boolean result = simpleRedisTemplate.opsForValue().multiSetIfAbsent(map);
        if (result) {
            for (String key : keys) {
                simpleRedisTemplate.expire(key, DEFAULT_LOCK_EXPIRE_TIME, TimeUnit.SECONDS);
            }
            log.info("Thread id={} lock success", getCurrentThreadId());
        }
        return result;
    }

    protected boolean tryLock(String key) {
        Boolean result = simpleRedisTemplate.opsForValue().setIfAbsent(key, getCurrentThreadId());
        if (result) {
            simpleRedisTemplate.expire(key, DEFAULT_LOCK_EXPIRE_TIME, TimeUnit.SECONDS);
            log.info("Thread id={} lock success", getCurrentThreadId());
        }
        return result;
    }

    protected boolean unLock(String[] keys) {
        for (String key : keys) {
            unLock(key);
        }
        return true;
    }

    protected boolean unLock(String key) {
        String threadId = simpleRedisTemplate.getObject(key, String.class);
        String currentThreadId = getCurrentThreadId();
        if (null == threadId) {return true;}
        if (threadId.equals(currentThreadId)) {
            simpleRedisTemplate.delete(key);
            log.info("Thread id={}" + "unlock success", currentThreadId);
            return true;
        }
        return false;
    }

    private String getCurrentThreadId(){
        return String.valueOf(Thread.currentThread().getId())+ ServerRunTimeUtil.getMac();
    }
}
