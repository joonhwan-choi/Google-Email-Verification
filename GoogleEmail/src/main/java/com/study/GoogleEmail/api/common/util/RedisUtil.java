package com.study.GoogleEmail.api.common.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisUtil(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setDataExpire(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    public String getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean existData(String key) {
        return redisTemplate.hasKey(key);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }
}
