package com.xh.sso.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @Name TokenStoreConfig
 * @Description 自定义accessToken配置
 * @Author wen
 * @Date 2020-04-19
 */
@Configuration
public class TokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 配置token以redis存储方式
     */
    @Bean
    public TokenStore redisConnectionTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

}
