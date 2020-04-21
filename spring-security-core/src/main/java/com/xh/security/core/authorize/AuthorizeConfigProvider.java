package com.xh.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @Name AuthorizeConfigProvider
 * @Description 授权配置提供者接口（用户通过实现该接口配置 是否需要认证和权限的路径 ）
 * @Author wen
 * @Date 2020-04-21
 */
public interface AuthorizeConfigProvider {

    void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
