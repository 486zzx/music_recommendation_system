package com.zzx.zzx_music_recommendation_system.login;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class LoginUserCache {
    private Cache<String, UserDetails> cacheMap;

    /**
     * session超时时间，30分钟
     */
    public static final Integer SESSION_TIMEOUT = 120;

    @PostConstruct
    private void initCacheMap() {
        cacheMap = CacheBuilder.newBuilder()
                .expireAfterAccess(SESSION_TIMEOUT, TimeUnit.MINUTES)
                .build();
    }

    public void set(String token, UserDetails loginUser) {
        log.info("set sessionId: {}", token);
        cacheMap.put(token, loginUser);
    }

    public void delete(String token) {
        log.info("delete token: {}", token);
        cacheMap.invalidate(token);
    }

    public UserDetails get(String token) {
        return cacheMap.getIfPresent(token);
    }

    public Cache<String, UserDetails> getCacheMap() {
        return cacheMap;
    }
}
