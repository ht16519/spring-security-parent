package com.xh.security.config;

import com.xh.security.authentiation.oauth2.config.OAuth2AuthenticationSecurityConfig;
import com.xh.security.authentiation.validate.config.SmsCodeAuthenticationSecurityConfig;
import com.xh.security.authentiation.validate.config.ValidateCodeSecurityConfig;
import com.xh.security.consts.KeyConst;
import com.xh.security.consts.URLConst;
import com.xh.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Qualifier(KeyConst.CUSTOM_AUTHENTICATION_SUCCESS_HANDLER_BEAN_NAME)
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    @Qualifier(KeyConst.CUSTOM_AUTHENTICATION_FAILURE_HANDLER_BEAN_NAME)
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    @Qualifier(KeyConst.CUSTOM_LOGOUT_SUCCESS_HANDLER_BEAN_NAME)
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;
    @Autowired
    private OAuth2AuthenticationSecurityConfig oauth2AuthenticationSecurityConfig;

    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        http.apply(oauth2AuthenticationSecurityConfig)          //将OAuth2.0第三方登录逻辑配置加入
                .and()
            .apply(validateCodeSecurityConfig)                  //将验证码认证逻辑配置加入
                .and()
            .apply(smsCodeAuthenticationSecurityConfig)         //将手机短信登录校验逻辑配置加入
                .and()
            .formLogin()
                .loginPage(URLConst.REQUIRE_AUTHENTICATION_PATH)                //自定义登录认证请求处理Controller路径1.2
                .loginProcessingUrl(URLConst.AUTHENTICATION_FORM_PATH)              //自定义处理登录页面的请求路径1.1
                .successHandler(successHandler)                             //使用自定义登录成功处理器
                .failureHandler(failureHandler)                             //使用自定义登录失败处理器
                .and()
            .logout()
                .logoutUrl(securityProperties.getLogoutUrl())
                .logoutSuccessHandler(logoutSuccessHandler);
    }

}
