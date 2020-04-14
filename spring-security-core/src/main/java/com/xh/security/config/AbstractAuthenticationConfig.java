package com.xh.security.config;

import com.xh.security.authentiation.oauth2.config.OAuth2AuthenticationSecurityConfig;
import com.xh.security.consts.KeyConst;
import com.xh.security.consts.URLConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @Name AbstractAuthenticationConfig
 * @Description 抽象安全认证配置
 * @Author wen
 * @Date 2020-04-11
 */
public class AbstractAuthenticationConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier(KeyConst.CUSTOM_AUTHENTICATION_SUCCESS_HANDLER_BEAN_NAME)
    protected AuthenticationSuccessHandler successHandler;
    @Autowired
    @Qualifier(KeyConst.CUSTOM_AUTHENTICATION_FAILURE_HANDLER_BEAN_NAME)
    protected AuthenticationFailureHandler failureHandler;

    @Autowired
    private OAuth2AuthenticationSecurityConfig oauth2AuthenticationSecurityConfig;


    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        http.apply(oauth2AuthenticationSecurityConfig)
            .and()
            .formLogin()
            .loginPage(URLConst.REQUIRE_AUTHENTICATION_PATH)          //自定义登录认证请求处理Controller路径1.2
            .loginProcessingUrl(URLConst.AUTHENTICATION_FORM_PATH)              //自定义处理登录页面的请求路径1.1
            .successHandler(successHandler)                           //使用自定义登录成功处理器
            .failureHandler(failureHandler);                             //使用自定义登录失败处理器
    }

}
