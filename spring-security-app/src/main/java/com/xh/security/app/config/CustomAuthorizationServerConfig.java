package com.xh.security.app.config;

import com.xh.security.core.properties.OAuth2ClientProperties;
import com.xh.security.core.properties.SecurityProperties;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name CustomAuthorizationServerConfig
 * @Description 认证服务器配置类
 * @Author wen
 * @Date 2020-04-16
 */
@Configuration
@EnableAuthorizationServer
public class CustomAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;
    @Autowired
    SecurityProperties securityProperties;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;
    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationConfiguration.getAuthenticationManager())
                .userDetailsService(userDetailsService);
        //自定义JWT的accessToken配置
        if (null != jwtAccessTokenConverter) {
            endpoints.accessTokenConverter(jwtAccessTokenConverter);
        }
        //配置accessToken增强器链
        if (null != jwtTokenEnhancer) {
            TokenEnhancerChain chain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>(2);
            enhancers.add(jwtTokenEnhancer);
            enhancers.add(jwtAccessTokenConverter);
            chain.setTokenEnhancers(enhancers);
            endpoints.tokenEnhancer(chain);
        }
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        //加载用户配置
        OAuth2ClientProperties[] clientsProperties = securityProperties.getOauth2Support().getClients();
        if (ArrayUtils.isNotEmpty(clientsProperties)) {
            for (OAuth2ClientProperties clientProperties : clientsProperties) {
                builder.withClient(clientProperties.getClientId())                          //配置clientId
                        .secret(clientProperties.getClientSecret())                                      //配置secretId
                        .accessTokenValiditySeconds(clientProperties.getAccessTokenValiditySeconds())    //配置token失效时间
                        .refreshTokenValiditySeconds(clientProperties.getRefreshTokenValiditySeconds())  //配置refreshToken失效时间
                        .scopes(clientProperties.getScopes())                         //配置拥有的权限
                        .authorizedGrantTypes(clientProperties.getGrantTypes())   //配置授权方式
                        .redirectUris(clientProperties.getRedirectUris());          //配置定向地址
            }
        }
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()")
                .passwordEncoder(NoOpPasswordEncoder.getInstance());//TODO 暂时不加密
    }
}
