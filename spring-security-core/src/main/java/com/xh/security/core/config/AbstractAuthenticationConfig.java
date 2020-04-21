package com.xh.security.core.config;

import com.xh.security.core.authentiation.oauth2.config.OAuth2AuthenticationSecurityConfig;
import com.xh.security.core.authentiation.validate.config.SmsCodeAuthenticationSecurityConfig;
import com.xh.security.core.authentiation.validate.config.ValidateCodeSecurityConfig;
import com.xh.security.core.authorize.AuthorizeConfigManager;
import com.xh.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @Name AbstractAuthenticationConfig
 * @Description 公共的安全认证配置提供者
 * @Author wen
 * @Date 2020-04-11
 */
public class AbstractAuthenticationConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected SecurityProperties securityProperties;
    @Autowired
//    @Qualifier(BeanNameConst.CUSTOM_USER_DETAILS_SERVICE_BEAN_NAME)
    protected UserDetailsService userDetailsService;
//    @Autowired
//    @Qualifier(BeanNameConst.CUSTOM_PASSWORD_ENCODER_BEAN_NAME)
//    protected PasswordEncoder passwordEncoder;
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
    protected AuthorizeConfigManager authorizeConfigManager;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;
    @Autowired(required = false)
    private OAuth2AuthenticationSecurityConfig oauth2AuthenticationSecurityConfig;

    public void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        //将OAuth2.0第三方登录逻辑配置加入
        if (oauth2AuthenticationSecurityConfig != null) {
            http.apply(oauth2AuthenticationSecurityConfig);
        }
        http.apply(validateCodeSecurityConfig)                  //将验证码认证逻辑配置加入
                .and()
                .apply(smsCodeAuthenticationSecurityConfig);         //将手机短信登录校验逻辑配置加入
        //登出配置
        http.logout()
                .logoutUrl(securityProperties.getLogoutUrl())               //用户登出请求地址
                .logoutSuccessHandler(logoutSuccessHandler)                 //登出成功处理器
                .deleteCookies("JSESSIONID");                                //删除cookies
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//    }

}
