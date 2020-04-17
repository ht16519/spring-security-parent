package com.xh.security.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()   //指定身份认证的方式为表单登录
                .and()
                .authorizeRequests()                                //授权请求1.0
//                .antMatchers(permitUrls()).permitAll()              //指定放行路径1.2
                .anyRequest()                                       //拦截所有1.0
                .authenticated()                                    //需要认证1.0
                .and()
                .csrf().disable();
    }
}
