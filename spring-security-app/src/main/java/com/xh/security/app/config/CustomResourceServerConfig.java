package com.xh.security.app.config;

import com.xh.security.core.authentiation.oauth2.config.OAuth2AuthenticationSecurityConfig;
import com.xh.security.core.authentiation.validate.config.SmsCodeAuthenticationSecurityConfig;
import com.xh.security.core.authentiation.validate.config.ValidateCodeSecurityConfig;
import com.xh.security.core.consts.BeanNameConst;
import com.xh.security.core.consts.URLConst;
import com.xh.security.core.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import java.util.HashSet;
import java.util.Set;

/**
 * @Name CustomResourceServerConfig
 * @Description 资源服务器配置
 * @Author wen
 * @Date 2020-04-17
 */
@Configuration
@EnableResourceServer
public class CustomResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    protected SecurityProperties securityProperties;

    @Autowired
    @Qualifier(BeanNameConst.APP_AUTHENTICATION_SUCCESS_HANDLER_BEAN_NAME)
    protected AuthenticationSuccessHandler successHandler;
    @Autowired
    @Qualifier(BeanNameConst.APP_AUTHENTICATION_FAILURE_HANDLER_BEAN_NAME)
    protected AuthenticationFailureHandler failureHandler;
    @Autowired
    @Qualifier(BeanNameConst.APP_LOGOUT_SUCCESS_HANDLER_BEAN_NAME)
    protected LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;
    @Autowired(required = false)
    private OAuth2AuthenticationSecurityConfig oauth2AuthenticationSecurityConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        if(oauth2AuthenticationSecurityConfig != null){
            http.apply(oauth2AuthenticationSecurityConfig);          //将OAuth2.0第三方登录逻辑配置加入
        }
        http.apply(validateCodeSecurityConfig)                  //将验证码认证逻辑配置加入
                .and()
        .apply(smsCodeAuthenticationSecurityConfig);         //将手机短信登录校验逻辑配置加入

        http.formLogin()
                .loginPage(URLConst.REQUIRE_AUTHENTICATION_PATH)                //自定义登录认证请求处理Controller路径1.2
                .loginProcessingUrl(URLConst.AUTHENTICATION_FORM_PATH)              //自定义处理登录页面的请求路径1.1
                .successHandler(successHandler)                             //使用自定义登录成功处理器
                .failureHandler(failureHandler)                             //使用自定义登录失败处理器
                .and()
            .logout()
                .logoutUrl(securityProperties.getLogoutUrl())               //用户登出请求地址
                .logoutSuccessHandler(logoutSuccessHandler)                 //登出成功处理器
                .and()
            .authorizeRequests()                                //授权请求1.0
                .antMatchers(permitUrls()).permitAll()              //指定放行路径1.2
                .anyRequest()                                       //拦截所有1.0
                .authenticated()                                    //需要认证1.0
                .and()
            .csrf().disable();                                  //关闭跨站伪造攻击的防护1.2
    }

    /**
     * 指定放行路径
     */
    private String[] permitUrls() {
        Set<String> permitUrlSet = new HashSet<>();
        String permitUrls = securityProperties.getBrowser().getPermitUrls();
        for (String url : new String[]{
                URLConst.REQUIRE_AUTHENTICATION_PATH,               //放行自定义登录认证请求处理Controller路径
                URLConst.VALIDATE_IMAGE_CODE_PATH,                  //放行图片验证码生成路径
                URLConst.VALIDATE_SMS_CODE_PATH,                    //放行短信验证码生成路径
                URLConst.HANDLE_SESSION_INVALID_URL,                //放行处理session失效地址
                URLConst.LOGOUT_PATH,                               //放行登出默认路径
                "/static/**",                                       //放行静态资源
                "/oauth2/**",
        }) {
            permitUrlSet.add(url);
        }
        if(StringUtils.isNotBlank(permitUrls)){
            for (String url : permitUrls.split(",")) {
                permitUrlSet.add(url);
            }
        }
        return permitUrlSet.toArray(new String[permitUrlSet.size()]);
    }
}
