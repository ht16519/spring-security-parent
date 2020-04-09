package com.xh.security.config;

import com.xh.security.consts.URLConst;
import com.xh.security.handler.MyAuthenticationFaildHandler;
import com.xh.security.handler.MyAuthenticationSuccessHandler;
import com.xh.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Name BrowserSecurityConfig
 * @Description
 * @Author wen
 * @Date 2020-04-09
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFaildHandler myAuthenticationFaildHandler;

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(URLConst.REQUIRE_AUTHENTICATION_PATH)                    //自定义登录认证请求处理Controller路径1.2
                .loginProcessingUrl("/authentication/form")              //自定义处理登录页面的请求路径1.1
                .successHandler(myAuthenticationSuccessHandler)         //使用自定义登录成功处理器
                .failureHandler(myAuthenticationFaildHandler)           //使用自定义登录失败处理器
                .and()
                .authorizeRequests()        //授权请求1.0
                .antMatchers(securityProperties.getBrowser().getLoginPage(),    //放行跳转到登录页面的请求
                        URLConst.REQUIRE_AUTHENTICATION_PATH)  //放行自定义登录认证请求处理Controller路径
                .permitAll()          //指定放行路径1.2
                .anyRequest()                                       //拦截所有1.0
                .authenticated()                                    //需要认证1.0
                .and()
                .csrf().disable();                                  //关闭跨站伪造攻击的防护1.2
    }
}
