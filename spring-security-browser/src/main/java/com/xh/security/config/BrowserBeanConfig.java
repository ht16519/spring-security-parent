package com.xh.security.config;

import com.xh.security.consts.KeyConst;
import com.xh.security.handler.CustomAuthenticationFailureHandler;
import com.xh.security.handler.CustomAuthenticationSuccessHandler;
import com.xh.security.handler.strategy.DefaultTimeExpiredSessionStrategy;
import com.xh.security.session.strategy.DefaultConcurrentLoginSessionInvalidStrategy;
import com.xh.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
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

    /** 配置登录成功处理器*/
    @Bean(KeyConst.CUSTOM_AUTHENTICATION_SUCCESS_HANDLER_BEAN_NAME)
    @ConditionalOnMissingBean(name = KeyConst.CUSTOM_AUTHENTICATION_SUCCESS_HANDLER_BEAN_NAME)
    public SavedRequestAwareAuthenticationSuccessHandler successHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    /** 配置登录失败处理器*/
    @Bean(KeyConst.CUSTOM_AUTHENTICATION_FAILURE_HANDLER_BEAN_NAME)
    @ConditionalOnMissingBean(name = KeyConst.CUSTOM_AUTHENTICATION_FAILURE_HANDLER_BEAN_NAME)
    public SimpleUrlAuthenticationFailureHandler failureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    /** 配置默认session超时失效处理策略*/
    @Bean(KeyConst.TIME_EXPIRED_SESSION_STRATEGY_BEAN_NAME)
    @ConditionalOnMissingBean(name = KeyConst.TIME_EXPIRED_SESSION_STRATEGY_BEAN_NAME)
    public InvalidSessionStrategy invalidSessionStrategy(){
        return new DefaultTimeExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

}
