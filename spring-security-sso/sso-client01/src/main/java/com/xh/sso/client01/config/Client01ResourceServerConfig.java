package com.xh.sso.client01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * @Name Client01ResourceServerConfig
 * @Description 资源服务器配置
 * @Author wen
 * @Date 2020-04-23
 */
@Configuration
@EnableResourceServer
public class Client01ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("client01");   //配置当前资源服务器的名称
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET).access("#oauth2.hasScope('read')") //配置所有GET请求需要read权限
                .antMatchers(HttpMethod.POST).access("#oauth2.hasScope('wirte')");//配置所有POST请求需要wirte权限
    }

    @Bean
    @Primary
    public ResourceServerTokenServices tokenServices() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setClientId("ht16519");
        tokenServices.setClientSecret("ht16519");
        tokenServices.setCheckTokenEndpointUrl("http://localhost:9999/auth/oauth/check_token");
        return tokenServices;
    }

    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
        authenticationManager.setTokenServices(tokenServices());
        return authenticationManager;
    }

}
