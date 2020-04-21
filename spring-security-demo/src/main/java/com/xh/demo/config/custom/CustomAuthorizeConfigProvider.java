package com.xh.demo.config.custom;

import com.xh.security.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @Name CustomAuthorizeConfigProvider
 * @Description 自定义授权配置提供者
 * @Author wen
 * @Date 2020-04-21
 */
@Order      //自定义的授权配置提供者加载顺序应该最大（最后加载）
@Component
public class CustomAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

    }
}
