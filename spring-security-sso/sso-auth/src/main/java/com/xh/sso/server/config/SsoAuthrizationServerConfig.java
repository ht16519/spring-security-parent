package com.xh.sso.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.JdbcClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

/**
 * @Name SsoAuthrizationServerConfig
 * @Description 认证服务器配置类
 * @Author wen
 * @Date 2020-04-20
 */
@Configuration
@EnableAuthorizationServer
public class SsoAuthrizationServerConfig extends AuthorizationServerConfigurerAdapter {


    @Autowired
    private DataSource dataSource;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        JdbcClientDetailsServiceBuilder builder = clients.jdbc(dataSource);
//        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        //加载用户配置
//        builder.withClient("ht16519")                          //配置clientId
//                .secret(passwordEncoder.encode("ht16519"))                                      //配置secretId
//                .scopes("read", "wirte")                         //配置拥有的权限
//                .authorizedGrantTypes("refresh_token", "authorization_code", "password")   //配置授权方式
//                .redirectUris("http://localhost:9001/client01/login")         //配置定向地址
//                .resourceIds("client01")            //配置可以访问的资源服务器列表
//                .and()
//                .withClient("ht17520")                          //配置clientId
//                .secret(passwordEncoder.encode("ht17520"))                                      //配置secretId
//                .scopes("all", "read", "wirte")                         //配置拥有的权限
//                .authorizedGrantTypes("refresh_token", "authorization_code", "password")   //配置授权方式
//                .redirectUris("http://localhost:9002/client02/login")          //配置定向地址
//                .resourceIds("client02");            //配置可以访问的资源服务器列表
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore);
        endpoints.authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()")        //配置校验Token需要带上clientId和secretId
    //        .tokenKeyAccess("isAuthenticated()")         //配置访问signingKey密钥需要带上clientId和secretId
                .passwordEncoder(passwordEncoder);//TODO 暂时不加密
//        security.allowFormAuthenticationForClients()
//                .checkTokenAccess("permitAll()");
//
    }

    /**
     * 配置token以JWT方式返回
     */
//    @Bean
//    public TokenStore jwtTokenStore() {
//        return new JwtTokenStore(jwtAccessTokenConverter());
//    }

    /**
     * 配置JWT的accessToken转化器
     */
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter() {
//        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//        jwtAccessTokenConverter.setSigningKey("ht18522");
//        return jwtAccessTokenConverter;
//    }

}
