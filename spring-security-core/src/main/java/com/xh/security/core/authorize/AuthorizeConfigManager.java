package com.xh.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @Name AuthorizeConfigManager
 * @Description 认证授权配置提供管理器接口
 * @Author wen
 * @Date 2020-04-21
 */
public interface AuthorizeConfigManager {

    void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
