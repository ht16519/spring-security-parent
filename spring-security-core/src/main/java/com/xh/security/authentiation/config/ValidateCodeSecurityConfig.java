package com.xh.security.authentiation.config;

import com.xh.security.authentiation.filter.ValidateCodeAuthenticationFilter;
import com.xh.security.authentiation.mobile.SmsCodeAuthenticationFilter;
import com.xh.security.authentiation.mobile.SmsCodeAuthenticationProvider;
import com.xh.security.authentiation.mobile.details.UserDetails4MobileService;
import com.xh.security.authentiation.processor.ValidateCodeProcessor;
import com.xh.security.consts.KeyConst;
import com.xh.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
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
 * @Description 短信验证码认证的安全配置类
 * @Author wen
 * @Date 2020-04-10
 */
@Configuration
public class ValidateCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessor;
    @Autowired
    @Qualifier(KeyConst.CUSTOM_AUTHENTICATION_FAILURE_HANDLER_BEAN_NAME)
    private AuthenticationFailureHandler failureHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(
                new ValidateCodeAuthenticationFilter(failureHandler, securityProperties, validateCodeProcessor),
                UsernamePasswordAuthenticationFilter.class);  //将验证码校验过滤器放在UsernamePasswordAuthenticationFilter之前
    }
}
