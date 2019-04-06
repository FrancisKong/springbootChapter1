package com.linfengda.sb.chapter1.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linfengda.sb.support.middleware.redis.SimpleRedisTemplate;
import com.linfengda.sb.support.middleware.redis.SimpleRedisTemplate4JS;
import com.linfengda.sb.support.middleware.redis.SimpleRedisTemplate4PS;
import com.linfengda.sb.support.middleware.redis.serializer.ProtoStuffSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;

/**
 * 描述: Redis配置
 *
 * @author linfengda
 * @create 2018-09-10 17:00
 */
@SpringBootConfiguration
public class RedisConfig {
    @Value("${spring.redis.serializer}")
    private String serializer;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.database}")
    private int database;


    /**
     * Jedis客户端
     * @return
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {

        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(host);
        standaloneConfiguration.setPort(port);
        standaloneConfiguration.setDatabase(database);
        // 获取连接管理工厂
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(standaloneConfiguration);
        return jedisConnectionFactory;
    }

    @Bean
    public SimpleRedisTemplate simpleRedisTemplate(JedisConnectionFactory connectionFactory) {
        Serializer serializer = Serializer.getType(this.serializer);
        Assert.notNull(serializer, "序列化方式不能为空！");

        SimpleRedisTemplate simpleRedisTemplate = getRedisTemplate(serializer);
        RedisSerializer redisSerializer = getRedisSerializer(serializer);
        simpleRedisTemplate.setConnectionFactory(connectionFactory);
        // 配置序列化和反序列化方式
        simpleRedisTemplate.setKeySerializer(new StringRedisSerializer());
        simpleRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        simpleRedisTemplate.setValueSerializer(redisSerializer);
        simpleRedisTemplate.setHashValueSerializer(redisSerializer);
        //simpleRedisTemplate.afterPropertiesSet();
        return simpleRedisTemplate;
    }

    private SimpleRedisTemplate getRedisTemplate(Serializer serializer) {
        switch (serializer) {
            case protoStuff:
                return new SimpleRedisTemplate4PS();
            case jackson:
                return new SimpleRedisTemplate4JS();
            default:
                return null;
        }
    }

    private RedisSerializer getRedisSerializer(Serializer serializer) {
        switch (serializer) {
            case protoStuff:
                ProtoStuffSerializer protoStuffSerializer = new ProtoStuffSerializer();
                return protoStuffSerializer;
            case jackson:
                Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
                ObjectMapper om = new ObjectMapper();
                om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
                om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
                jackson2JsonRedisSerializer.setObjectMapper(om);
                return jackson2JsonRedisSerializer;
            default:
                return null;
        }
    }

    /**
     * 序列化类型
     */
    public enum Serializer {
        protoStuff, jackson;
        public static Serializer getType(String serializer) {
            for (Serializer v : values()) {
                if (v.name().equals(serializer)) {
                    return v;
                }
            }
            return jackson;
        }
    }
}