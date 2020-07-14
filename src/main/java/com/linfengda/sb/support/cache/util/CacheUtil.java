package com.linfengda.sb.support.cache.util;

import com.linfengda.sb.support.cache.config.Constant;

import java.util.Random;

/**
 * 描述: 缓存工具方法
 *
 * @author: linfengda
 * @date: 2020-07-13 10:22
 */
public class CacheUtil {

    /**
     * 叠加随机过期时间
     * @param timeOutMillis    原始过期时间
     * @return                 叠加后的过期时间
     */
    public static long getRandomTime(long timeOutMillis) {
        Random random = new Random();
        Integer randomTime = random.nextInt(Constant.DEFAULT_NO_CACHE_SNOW_SLIDE_RANDOM_MS);
        return timeOutMillis + randomTime;
    }
}
