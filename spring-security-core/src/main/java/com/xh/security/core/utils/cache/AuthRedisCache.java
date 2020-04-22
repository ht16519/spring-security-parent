package com.xh.security.core.utils.cache;

import com.xh.security.core.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 基于redis的缓存实现
 */
public class AuthRedisCache implements AuthCache {

    private StringRedisTemplate stringRedisTemplate;

    public AuthRedisCache(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 设置缓存
     *
     * @param key   缓存KEY
     * @param value 缓存内容
     */
    @Override
    public void set(String key, String value) {
        set(key, value, AuthCacheConfig.timeout);
    }

    /**
     * 设置缓存
     *
     * @param key     缓存KEY
     * @param value   缓存内容
     * @param timeout 指定缓存过期时间（毫秒）
     */
    @Override
    public void set(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取缓存
     *
     * @param key 缓存KEY
     * @return 缓存内容
     */
    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        String v = stringRedisTemplate.opsForValue().get(key);
        if(StringUtils.isEmpty(v)){
            return null;
        }
        return JsonUtil.parse(v, clazz);
    }

    /**
     * 是否存在key，如果对应key的value值已过期，也返回false
     *
     * @param key 缓存KEY
     * @return true：存在key，并且value没过期；false：key不存在或者已过期
     */
    @Override
    public boolean containsKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }



}
