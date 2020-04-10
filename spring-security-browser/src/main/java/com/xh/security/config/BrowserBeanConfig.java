package com.xh.security.config;

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

    @Bean("customAuthenticationSuccessHandler")
    @ConditionalOnMissingBean(name = "customAuthenticationSuccessHandler")
    public SavedRequestAwareAuthenticationSuccessHandler successHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean("customAuthenticationFailureHandler")
    @ConditionalOnMissingBean(name = "customAuthenticationFailureHandler")
    public SimpleUrlAuthenticationFailureHandler failureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
}
