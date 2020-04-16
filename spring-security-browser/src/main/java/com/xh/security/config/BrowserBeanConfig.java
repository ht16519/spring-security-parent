package com.xh.security.config;

import com.xh.security.consts.KeyConst;
import com.xh.security.properties.SecurityProperties;
import com.xh.security.session.strategy.DefaultConcurrentLoginSessionInvalidStrategy;
import com.xh.security.session.strategy.DefaultTimeExpiredSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * @Name BeanConfig
 * @Description
 * @Author wen
 * @Date 2020-04-10
 */
@Configuration
public class BrowserBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /** 配置默认session超时失效处理策略*/
    @Bean(KeyConst.TIME_EXPIRED_SESSION_STRATEGY_BEAN_NAME)
    @ConditionalOnMissingBean(name = KeyConst.TIME_EXPIRED_SESSION_STRATEGY_BEAN_NAME)
    public InvalidSessionStrategy invalidSessionStrategy(){
        return new DefaultTimeExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    /** 配置默认并发登录时session失效处理策略*/
    @Bean(KeyConst.CONCURRENT_LOGIN_SESSION_INVALID_STRATEGY_BEAN_NAME)
    @ConditionalOnMissingBean(name = KeyConst.CONCURRENT_LOGIN_SESSION_INVALID_STRATEGY_BEAN_NAME)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
        return new DefaultConcurrentLoginSessionInvalidStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

}
