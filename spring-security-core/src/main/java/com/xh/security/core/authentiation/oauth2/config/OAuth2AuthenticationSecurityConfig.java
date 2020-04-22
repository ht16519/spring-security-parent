package com.xh.security.core.authentiation.oauth2.config;

import com.xh.security.core.authentiation.oauth2.SocialAuthenticationFilter;
import com.xh.security.core.authentiation.oauth2.SocialAuthenticationProvider;
import com.xh.security.core.authentiation.oauth2.support.details.SocialUserDetailsService;
import com.xh.security.core.authentiation.oauth2.support.request.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;

/**
 * @Name SmsCodeAuthenticationSecurityConfig
 * @Description 将OAuth2.0认证的安全配置逻辑加入到security的过滤器链上
 * @Author wen
 * @Date 2020-04-10
 */
public class OAuth2AuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private AuthenticationSuccessHandler successHandler;

    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private SocialUserDetailsService userDetails4OAuth2Service;
    @Autowired
    private Map<String, AuthRequest> authRequestMap;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //配置OAuth2登录认证过滤器
        SocialAuthenticationFilter filter = new SocialAuthenticationFilter();
        filter.setAuthRequestMap(authRequestMap);
        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);

        //配置处理OAuth2AuthenticationFilter的provider
        SocialAuthenticationProvider provider = new SocialAuthenticationProvider();
        provider.setUserDetails4OAuth2Service(userDetails4OAuth2Service);
        http.authenticationProvider(provider)
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
    }

    public OAuth2AuthenticationSecurityConfig(AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }
}
