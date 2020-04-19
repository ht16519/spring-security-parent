package com.xh.security.core.config;

import com.xh.security.core.authentiation.oauth2.config.OAuth2AuthenticationSecurityConfig;
import com.xh.security.core.authentiation.validate.config.SmsCodeAuthenticationSecurityConfig;
import com.xh.security.core.authentiation.validate.config.ValidateCodeSecurityConfig;
import com.xh.security.core.consts.BeanNameConst;
import com.xh.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @Name AbstractAuthenticationConfig
 * @Description 抽象安全认证配置
 * @Author wen
 * @Date 2020-04-11
 */
public class AbstractAuthenticationConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected SecurityProperties securityProperties;

    @Autowired
//    @Qualifier(BeanNameConst.APP_AUTHENTICATION_SUCCESS_HANDLER_BEAN_NAME)
    protected AuthenticationSuccessHandler successHandler;
    @Autowired
//    @Qualifier(BeanNameConst.APP_AUTHENTICATION_FAILURE_HANDLER_BEAN_NAME)
    protected AuthenticationFailureHandler failureHandler;
    @Autowired
//    @Qualifier(BeanNameConst.APP_LOGOUT_SUCCESS_HANDLER_BEAN_NAME)
    protected LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;
    @Autowired(required = false)
    private OAuth2AuthenticationSecurityConfig oauth2AuthenticationSecurityConfig;

    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        if(oauth2AuthenticationSecurityConfig != null){
            http.apply(oauth2AuthenticationSecurityConfig);          //将OAuth2.0第三方登录逻辑配置加入
        }
        http.apply(validateCodeSecurityConfig)                  //将验证码认证逻辑配置加入
                .and()
            .apply(smsCodeAuthenticationSecurityConfig);         //将手机短信登录校验逻辑配置加入
    }

}
