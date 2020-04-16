package com.xh.security.config;

import com.xh.security.consts.KeyConst;
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
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

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
    private DataSource dataSource;
    @Autowired
    @Qualifier(KeyConst.CUSTOM_USER_DETAILS_SERVICE_BEAN_NAME)
    private UserDetailsService userDetailsService;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);  //启动时创建记住我的数据库表
        return jdbcTokenRepository;
    }

    @Autowired
    @Qualifier(KeyConst.CONCURRENT_LOGIN_SESSION_INVALID_STRATEGY_BEAN_NAME)
    private SessionInformationExpiredStrategy concurrentLoginExpiredSessionStrategy;

    @Autowired
    @Qualifier(KeyConst.TIME_EXPIRED_SESSION_STRATEGY_BEAN_NAME)
    private InvalidSessionStrategy timeExpiredSessionStrategy;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //加载父类配置
        applyPasswordAuthenticationConfig(http);
        //浏览器安全配置
        http.formLogin()
                .loginPage(URLConst.REQUIRE_AUTHENTICATION_PATH)                //自定义登录认证请求处理Controller路径1.2
                .loginProcessingUrl(URLConst.AUTHENTICATION_FORM_PATH)              //自定义处理登录页面的请求路径1.1
                .successHandler(successHandler)                             //使用自定义登录成功处理器
                .failureHandler(failureHandler)                             //使用自定义登录失败处理器
                .and()
            .logout()
                .logoutUrl(securityProperties.getLogoutUrl())               //用户登出请求地址
                .logoutSuccessHandler(logoutSuccessHandler)                 //登出成功处理器
                .deleteCookies("JSESSIONID")                               //删除cookies
                .and()
            .rememberMe()                                                   //配置记住我功能
                .tokenRepository(persistentTokenRepository())               //配置处理记住我token的数据库操作
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())   //配置记住我超时时间
                .userDetailsService(userDetailsService)             //配置记住我认证的userDetails
                .and()
            .sessionManagement()
                .invalidSessionStrategy(timeExpiredSessionStrategy)    //配置session超时失效处理
                .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())//配置最大并发登录session数
                .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())         //当并发session数达到最大，阻止后面用户登录
                .expiredSessionStrategy(concurrentLoginExpiredSessionStrategy)   //并发登录时session失效处理
                .and()
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
                securityProperties.getBrowser().getLoginPage(),    //放行跳转到登录页面的请求
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
