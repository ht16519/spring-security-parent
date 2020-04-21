package com.xh.security.app.config;

import com.xh.security.app.handler.AppAuthenticationFailureHandler;
import com.xh.security.app.handler.AppAuthenticationSuccessHandler;
import com.xh.security.app.handler.AppLogoutSuccessHandler;
import com.xh.security.core.consts.BeanNameConst;
import com.xh.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @Name AppBeanConfig
 * @Description
 * @Author wen
 * @Date 2020-04-17
 */
@Configuration
public class AppBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 配置登录成功处理器
     */
    @Bean
//    @Bean(BeanNameConst.APP_AUTHENTICATION_SUCCESS_HANDLER_BEAN_NAME)
//    @ConditionalOnMissingBean(name = BeanNameConst.APP_AUTHENTICATION_SUCCESS_HANDLER_BEAN_NAME)
    public SavedRequestAwareAuthenticationSuccessHandler successHandler() {
        return new AppAuthenticationSuccessHandler();
    }

    /**
     * 配置登录失败处理器
     */
    @Bean
//    @Bean(BeanNameConst.APP_AUTHENTICATION_FAILURE_HANDLER_BEAN_NAME)
//    @ConditionalOnMissingBean(name = BeanNameConst.APP_AUTHENTICATION_FAILURE_HANDLER_BEAN_NAME)
    public SimpleUrlAuthenticationFailureHandler failureHandler() {
        return new AppAuthenticationFailureHandler(securityProperties);
    }

    /**
     * 配置登录失败处理器
     */
    @Bean
//    @Bean(BeanNameConst.APP_LOGOUT_SUCCESS_HANDLER_BEAN_NAME)
//    @ConditionalOnMissingBean(name = BeanNameConst.APP_LOGOUT_SUCCESS_HANDLER_BEAN_NAME)
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new AppLogoutSuccessHandler(securityProperties);
    }

}
