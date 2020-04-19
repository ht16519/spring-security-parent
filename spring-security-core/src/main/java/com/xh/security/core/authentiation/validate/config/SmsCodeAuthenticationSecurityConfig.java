package com.xh.security.core.authentiation.validate.config;

import com.xh.security.core.authentiation.validate.mobile.SmsCodeAuthenticationFilter;
import com.xh.security.core.authentiation.validate.mobile.SmsCodeAuthenticationProvider;
import com.xh.security.core.authentiation.validate.mobile.details.UserDetails4MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Name SmsCodeAuthenticationSecurityConfig
 * @Description 将短信验证码认证的安全配置逻辑加入到security的过滤器链上
 * @Author wen
 * @Date 2020-04-10
 */
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private AuthenticationSuccessHandler successHandler;

    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private UserDetails4MobileService userDetails4MobileService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //配置手机号码登录认证过滤器
        SmsCodeAuthenticationFilter filter = new SmsCodeAuthenticationFilter();
        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);

        //配置处理SmsCodeAuthenticationFilter的provider
        SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider();
        provider.setUserDetails4MobileService(userDetails4MobileService);
        http.authenticationProvider(provider)
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
    }

    public SmsCodeAuthenticationSecurityConfig(AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }
}
