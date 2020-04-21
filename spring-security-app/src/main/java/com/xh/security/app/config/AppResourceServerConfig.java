package com.xh.security.app.config;

import com.xh.security.core.authorize.AuthorizeConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @Name CustomResourceServerConfig
 * @Description 资源服务器配置
 * @Author wen
 * @Date 2020-04-17
 */
@Configuration
@EnableResourceServer
public class AppResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;
    @Autowired
    private AppSecurityConfig appSecurityConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //加载配置
        appSecurityConfig.applyPasswordAuthenticationConfig(http);

        http.csrf().disable();                                  //关闭跨站伪造攻击的防护1.2

        //加载认证授权配置
        authorizeConfigManager.configure(http.authorizeRequests());
    }

}
