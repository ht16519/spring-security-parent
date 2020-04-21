package com.xh.sso.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Name SecurityConfig
 * @Description 安全配置类
 * @Author wen
 * @Date 2020-04-21
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    protected UserDetailsService userDetailsService;
//    @Autowired
//    protected PasswordEncoder passwordEncoder;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()              //登出成功处理器
                .and()
                .authorizeRequests()                                //授权请求1.0
                .anyRequest()                                       //拦截所有1.0
                .authenticated()                                    //需要认证1.0
                .and()
                .csrf().disable();                                  //关闭跨站伪造攻击的防护1.2
    }

    //    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//    }

}
