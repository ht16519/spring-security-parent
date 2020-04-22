package com.xh.security.core.utils.cache;

/**
 * 数据缓存接口
 */
public interface AuthCache {

    /**
     * 设置缓存
     *
     * @param key   缓存KEY
     * @param value 缓存内容
     */
    default void set(String key, String value) {
        throw new UnsupportedOperationException();
    }

    /**
     * 设置缓存，指定过期时间
     *
     * @param key     缓存KEY
     * @param value   缓存内容
     * @param timeout 指定缓存过期时间（毫秒）
     */
    default void set(String key, String value, long timeout) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取缓存
     *
     * @param key 缓存KEY
     * @return 缓存内容
     */
    default String get(String key) {
        throw new UnsupportedOperationException();
    }

    /**
     * 是否存在key，如果对应key的value值已过期，也返回false
     *
     * @param key 缓存KEY
     * @return true：存在key，并且value没过期；false：key不存在或者已过期
     */
    default boolean containsKey(String key) {
        throw new UnsupportedOperationException();
    }

    /**
     * 清理过期的缓存
     */
    default void pruneCache() {
        throw new UnsupportedOperationException();
    }

    /**
     * 移除
     */
    void remove(String key);

    default <T> T get(String key, Class<T> clazz) {
        throw new UnsupportedOperationException();
    }
}
