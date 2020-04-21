package com.xh.security.browser.config;

import com.xh.security.core.authorize.AuthorizeConfigManager;
import com.xh.security.core.config.AbstractAuthenticationConfig;
import com.xh.security.core.consts.BeanNameConst;
import com.xh.security.core.consts.URLConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.sql.DataSource;

/**
 * @Name BrowserSecurityConfig
 * @Description 浏览器安全配置
 * @Author wen
 * @Date 2020-04-09
 */
@Configuration
public class BrowserSecurityConfig extends AbstractAuthenticationConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);  //启动时创建记住我的数据库表
        return jdbcTokenRepository;
    }

    @Autowired
    @Qualifier(BeanNameConst.CONCURRENT_LOGIN_SESSION_INVALID_STRATEGY_BEAN_NAME)
    private SessionInformationExpiredStrategy concurrentLoginExpiredSessionStrategy;

    @Autowired
    @Qualifier(BeanNameConst.TIME_EXPIRED_SESSION_STRATEGY_BEAN_NAME)
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
            .csrf().disable();                                  //关闭跨站伪造攻击的防护1.2
        //加载认证授权配置
        authorizeConfigManager.configure(http.authorizeRequests());
    }

}
