package com.zzx.zzx_music_recommendation_system.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/18 21:45
 */
@Configuration
public class RedisSerializableConfig {
    @Bean
    public RedisTemplate<String,Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        //默认的序列化策略是JDK的，所以在存储的时候键和值都会先被序列化后再存储
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
        //1.修改键的序列化策略
        template.setKeySerializer(new StringRedisSerializer());
        //2.修改hash类型的value中key的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        //4.设置value的序列化策略
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //5.修改hash类型value中value的序列化方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        template.afterPropertiesSet();
        return template;
    }
}