//package com.xh.sso.client01.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
//import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
//import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
//
///**
// * @Name Client01SecurityConfig
// * @Description
// * @Author wen
// * @Date 2020-04-23
// */
////@Order(101)
////@Configuration
////@EnableWebSecurity
//public class Client01SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    @Primary
//    public ResourceServerTokenServices tokenServices(){
//        RemoteTokenServices tokenServices = new RemoteTokenServices();
//        tokenServices.setClientId("ht16519");
//        tokenServices.setClientSecret("ht16519");
//        tokenServices.setCheckTokenEndpointUrl("http://localhost:9999/server/oauth/check_token");
//        return tokenServices;
//    }
//
//    @Bean
//    @Override
//    protected AuthenticationManager authenticationManager() throws Exception {
//        OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
//        authenticationManager.setTokenServices(tokenServices());
//        return authenticationManager;
//    }
//}
