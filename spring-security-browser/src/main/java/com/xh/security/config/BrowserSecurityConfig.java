package com.xh.security.config;

import com.xh.security.consts.URLConst;
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

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(URLConst.SIMPLE_SIGNIN_PAGE_PATH)                        //自定义登录页面1.1
                .loginProcessingUrl(URLConst.AUTHENTICATION_FORM_PATH)              //自定义处理登录的请求路径1.1
                .and()
                .authorizeRequests()        //授权请求1.0
                .antMatchers(URLConst.SIMPLE_SIGNIN_PAGE_PATH).permitAll()          //指定放行路径1.1
                .anyRequest()               //拦截所有1.0
                .authenticated()           //需要认证1.0
                .and()
                .csrf().disable();          //关闭跨站伪造攻击的防护1.2
    }
}
