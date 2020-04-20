package com.xh.demo.config;

import com.xh.demo.config.custom.CustomJwtTokenEnhancer;
import com.xh.security.core.consts.BeanNameConst;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * @Name BeanConfig
 * @Description bean配置类
 * @Author wen
 * @Date 2020-04-20
 */
@Configuration
public class BeanConfig {

    @Bean(BeanNameConst.JWT_TOKEN_ENHANCER_BEAN_NAME)
    public TokenEnhancer jwtTokenEnhancer() {
        return new CustomJwtTokenEnhancer();
    }

}
