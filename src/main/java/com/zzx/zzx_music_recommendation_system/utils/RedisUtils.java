package com.zzx.zzx_music_recommendation_system.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzx.zzx_music_recommendation_system.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/14 21:54
 */
@Component
public class RedisUtils {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * 设置
     * @param type
     * @param key
     * @param value
     */
    public void set(Type type, String key, String value) {
        if (type.getExpire() != null) {
            stringRedisTemplate.opsForValue().set(type.getPrefix() + key, value, type.getExpire(), TimeUnit.SECONDS);
        } else {
            stringRedisTemplate.opsForValue().set(type.getPrefix() + key, value);
        }
    }

    public <T> void setList(Type type, String key, List<T> value) {
        redisTemplate.opsForList().rightPushAll(type.getPrefix() + key, value);
    }

    /**
     * 使用后要用
     *         List<?> users= objectMapper.convertValue(list, new TypeReference<List<?>>() {});
     *         转换
     * @param type
     * @param key
     * @return
     * @param <T>
     */
    public <T>List<T> getList(Type type, String key, TypeReference<List<T>> tTypeReference) {
        List<T> list = redisTemplate.opsForList().range(type.getPrefix() + key, 0, -1);
        List<T> res = objectMapper.convertValue(list, tTypeReference);
        return res;
    }

    public boolean deleteList(Type type, String key) {
        return redisTemplate.delete(type.getPrefix() + key);
    }


    public <T> T get(Type type, String key, Class<T> clazz) {
        try {
            String value = get(type, key);
            if (!StringUtils.hasText(value)) {
                return null;
            }
            return JSON.parseObject(value, clazz);
        } catch (Exception ex) {
            throw new MyException("JSON转换异常");
        }
    }

    public String get(Type type, String key) {
        return stringRedisTemplate.opsForValue().get(type.getPrefix() + key);
    }

    public enum Type {

        /**
         * 邮件验证码,保留5min
         */
        EMAIL_VALIDATE_CODE("EMAIL_CODE:", 5 * 60L),

        /**
         * 月排行
         */
        MOUTH_RANKING("MOUTH_RANK:", null),

        /**
         * 日排行
         */
        DAY_RANKING("DAY_RANK:", null)
        ;

        /**
         * 前缀
         */
        private final String prefix;
        /**
         * 到期
         */
        private final Long expire;

        Type(String prefix, Long expire) {
            this.prefix = prefix;
            this.expire = expire;
        }

        public String getPrefix() {
            return prefix;
        }

        public Long getExpire() {
            return expire;
        }

    }
}
