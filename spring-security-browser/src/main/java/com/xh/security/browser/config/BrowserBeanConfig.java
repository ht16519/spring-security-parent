package com.xh.security.browser.config;

import com.xh.security.browser.handler.DefaultAuthenticationFailureHandler;
import com.xh.security.browser.handler.DefaultAuthenticationSuccessHandler;
import com.xh.security.browser.handler.DefaultLogoutSuccessHandler;
import com.xh.security.browser.session.strategy.DefaultConcurrentLoginSessionInvalidStrategy;
import com.xh.security.browser.session.strategy.DefaultTimeExpiredSessionStrategy;
import com.xh.security.core.consts.BeanNameConst;
import com.xh.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
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

    /**
     * 配置默认session超时失效处理策略
     */
    @Bean(BeanNameConst.TIME_EXPIRED_SESSION_STRATEGY_BEAN_NAME)
    @ConditionalOnMissingBean(name = BeanNameConst.TIME_EXPIRED_SESSION_STRATEGY_BEAN_NAME)
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new DefaultTimeExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    /**
     * 配置默认并发登录时session失效处理策略
     */
    @Bean(BeanNameConst.CONCURRENT_LOGIN_SESSION_INVALID_STRATEGY_BEAN_NAME)
    @ConditionalOnMissingBean(name = BeanNameConst.CONCURRENT_LOGIN_SESSION_INVALID_STRATEGY_BEAN_NAME)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new DefaultConcurrentLoginSessionInvalidStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
    }

    /**
     * 配置登录成功处理器
     */
    @Bean
//    @Bean(BeanNameConst.BORWSER_AUTHENTICATION_SUCCESS_HANDLER_BEAN_NAME)
    @ConditionalOnMissingBean(name = BeanNameConst.BORWSER_AUTHENTICATION_SUCCESS_HANDLER_BEAN_NAME)
    public SavedRequestAwareAuthenticationSuccessHandler successHandler() {
        return new DefaultAuthenticationSuccessHandler(securityProperties);
    }

    /**
     * 配置登录失败处理器
     */
    @Bean
//    @Bean(BeanNameConst.BROWSER_AUTHENTICATION_FAILURE_HANDLER_BEAN_NAME)
    @ConditionalOnMissingBean(name = BeanNameConst.BROWSER_AUTHENTICATION_FAILURE_HANDLER_BEAN_NAME)
    public SimpleUrlAuthenticationFailureHandler failureHandler() {
        return new DefaultAuthenticationFailureHandler(securityProperties);
    }

    /**
     * 配置登出成功处理器
     */
    @Bean
//    @Bean(BeanNameConst.BROWSER_LOGOUT_SUCCESS_HANDLER_BEAN_NAME)
    @ConditionalOnMissingBean(name = BeanNameConst.BROWSER_LOGOUT_SUCCESS_HANDLER_BEAN_NAME)
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new DefaultLogoutSuccessHandler(securityProperties);
    }


}
