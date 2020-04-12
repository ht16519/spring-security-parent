package com.xh.security.config;

import com.xh.security.consts.KeyConst;
import com.xh.security.handler.CustomAuthenticationFailureHandler;
import com.xh.security.handler.CustomAuthenticationSuccessHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * @Name BeanConfig
 * @Description
 * @Author wen
 * @Date 2020-04-10
 */
@Configuration
public class BrowserBeanConfig {

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


}
