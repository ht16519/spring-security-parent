package com.xh.security.authentiation.mobile.config;

import com.xh.security.authentiation.mobile.SmsCodeAuthenticationFilter;
import com.xh.security.authentiation.mobile.SmsCodeAuthenticationProvider;
import com.xh.security.authentiation.mobile.details.UserDetails4MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Name SmsCodeAuthenticationSecurityConfig
 * @Description 短信验证码认证的安全配置类
 * @Author wen
 * @Date 2020-04-10
 */
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    @Qualifier("customAuthenticationSuccessHandler")
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    @Qualifier("customAuthenticationFailureHandler")
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    private UserDetails4MobileService userDetails4MobileService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        SmsCodeAuthenticationFilter filter = new SmsCodeAuthenticationFilter();
        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);

        SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider();
        provider.setUserDetails4MobileService(userDetails4MobileService);
        http.authenticationProvider(provider)
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
