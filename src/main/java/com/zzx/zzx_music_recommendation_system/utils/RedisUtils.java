package com.zzx.zzx_music_recommendation_system.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/14 21:54
 */
@Component
public class RedisUtils {

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisUtils(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

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
        EMAIL_VALIDATE_CODE("EMAIL:CODE", 5 * 60L)
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
