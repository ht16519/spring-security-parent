package com.xh.security.config;

import com.xh.security.authentiation.validate.config.SmsCodeAuthenticationSecurityConfig;
import com.xh.security.authentiation.validate.config.ValidateCodeSecurityConfig;
import com.xh.security.consts.URLConst;
import com.xh.security.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

/**
 * @Name BrowserSecurityConfig
 * @Description
 * @Author wen
 * @Date 2020-04-09
 */
@Configuration
public class BrowserSecurityConfig extends AbstractAuthenticationConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean("customPasswordEncoder")
    @ConditionalOnMissingBean(name = "customPasswordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private DataSource dataSource;
    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);  //启动时创建记住我的数据库表
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //加载父类配置
        applyPasswordAuthenticationConfig(http);
        http.apply(validateCodeSecurityConfig)                  //将验证码认证逻辑配置加入
                .and()
            .apply(smsCodeAuthenticationSecurityConfig)         //将手机短信登录校验逻辑配置加入
                .and()
            .rememberMe()                                                   //配置记住我功能
            .tokenRepository(persistentTokenRepository())               //配置处理记住我token的数据库操作
            .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())   //配置记住我超时时间
            .userDetailsService(userDetailsService)             //配置记住我认证的userDetails
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
        String permitUrls = securityProperties.getPermitUrls();
        for (String url : new String[]{
                securityProperties.getBrowser().getLoginPage(),    //放行跳转到登录页面的请求
                URLConst.REQUIRE_AUTHENTICATION_PATH,               //放行自定义登录认证请求处理Controller路径
                URLConst.VALIDATE_IMAGE_CODE_PATH,                  //放行图片验证码生成路径
                URLConst.VALIDATE_SMS_CODE_PATH,                    //放行短信验证码生成路径
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
