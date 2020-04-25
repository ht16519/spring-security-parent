package com.xh.sso.server.config;

import com.xh.sso.server.config.custom.TokenEnhancerExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
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
    @ConditionalOnProperty(prefix = "xh.security.oauth2Support", name = "storeType", havingValue = "redis")
    public TokenStore redisConnectionTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Bean
    @ConditionalOnProperty(prefix = "xh.security.oauth2Support", name = "storeType", havingValue = "redis")
    public TokenEnhancer tokenEnhancerExample(){
        return new TokenEnhancerExample();
    }


    @Configuration
    @ConditionalOnProperty(prefix = "xh.security.oauth2Support", name = "storeType", havingValue = "jwt", matchIfMissing = true)
    public static class JwtTokenConfig {
        /**
         * 配置token以JWT方式返回
         */
        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        /**
         * 配置JWT的accessToken转化器
         */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            jwtAccessTokenConverter.setSigningKey("ht18522");
            return jwtAccessTokenConverter;
        }
    }

}
