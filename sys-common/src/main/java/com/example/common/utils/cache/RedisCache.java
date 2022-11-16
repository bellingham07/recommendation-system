package com.example.common.utils.cache;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisCache {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 设置string cache
    public void setCache(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value));
    }

    // 设置带过期时间的string cache
    public void setCache(String key, String value, Long time, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, unit);
    }

    public <T> Map<String, Object> beanToMap(T value) {
        return BeanUtil.beanToMap(value, new HashMap<>(), new CopyOptions()
                .setIgnoreNullValue(true)
                .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
    }

    // 设置map cache
    public <T> void setMapCache(String key, T value) {
        stringRedisTemplate.opsForHash().putAll(key, beanToMap(value));
    }

    // 设置带过期时间的map cache
    public <T> void setMapCache(String key, T value, Long time, TimeUnit unit) {
        this.setMapCache(key, beanToMap(value));
        stringRedisTemplate.expire(key, time, unit);
    }

    // 获取string cache
    public String getCache(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    // 获取map cache
    public Map<Object, Object> getMapCache(String key) {
        return stringRedisTemplate.opsForHash().entries(key);
    }

    // string带逻辑过期
    public void setWithLogicalExpire(String key, Object value, Long time, TimeUnit unit) {
        // 设置逻辑过期
        RedisData redisData = new RedisData();
        redisData.setData(value);
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
        // 写入redis
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, unit);
    }

    public void expire(String key, Long ttl, TimeUnit unit) {
        stringRedisTemplate.expire(key, ttl, unit);
    }

    public void removeCache(String key) {
        stringRedisTemplate.delete(key);
    }
}
