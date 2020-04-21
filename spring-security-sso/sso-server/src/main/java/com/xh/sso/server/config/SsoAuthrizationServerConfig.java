package com.xh.sso.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @Name SsoAuthrizationServerConfig
 * @Description 认证服务器配置类
 * @Author wen
 * @Date 2020-04-20
 */
@Configuration
@EnableAuthorizationServer
public class SsoAuthrizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        //加载用户配置
        builder.withClient("ht16519")                          //配置clientId
                .secret("ht16519")                                      //配置secretId
                .scopes("all", "read", "wirte")                         //配置拥有的权限
                .authorizedGrantTypes("refresh_token", "authorization_code", "password")   //配置授权方式
                .redirectUris("http://localhost:9001/client01/login");          //配置定向地址

        builder.withClient("ht17520")                          //配置clientId
                .secret("ht17520")                                      //配置secretId
                .scopes("all", "read", "wirte")                         //配置拥有的权限
                .authorizedGrantTypes("refresh_token", "authorization_code", "password")   //配置授权方式
                .redirectUris("http://localhost:9002/client02/login");          //配置定向地址
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //配置token存储
        endpoints.tokenStore(jwtTokenStore());
        //自定义JWT的accessToken配置
        endpoints.accessTokenConverter(jwtAccessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("isAuthenticated()")
                .passwordEncoder(NoOpPasswordEncoder.getInstance());//TODO 暂时不加密
//        security.allowFormAuthenticationForClients()
//                .checkTokenAccess("permitAll()")
    }

    /**
     * 配置token以JWT方式返回
     */
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 配置JWT的accessToken转化器
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("ht18522");
        return jwtAccessTokenConverter;
    }

}
