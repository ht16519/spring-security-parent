package com.xh.security.core.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Name SecurityCoreAuthorizeConfigProvider
 * @Description 内置授权配置管理器（收集所有的AuthorizeConfigProvider提供的配置）
 * @Author wen
 * @Date 2020-04-21
 */
@Component
public class SecurityCoreAuthorizeConfigManager implements AuthorizeConfigManager {

    @Autowired
    private List<AuthorizeConfigProvider> authorizeConfigProviders;

    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        //1.设置所有AuthorizeConfigProvider提供的配置放行
        for (AuthorizeConfigProvider authorizeConfigProvider : authorizeConfigProviders) {
            authorizeConfigProvider.configure(config);
        }
        //2.其他所有接口拦截
        config.antMatchers("/member/**").hasRole("MEMBER")    //所有的会员接口需要验证会员权限
                .antMatchers("/sys/**")  //所有的后台接口,需要验证用户权限
                .access("hasRole('ADMIN') or @rbacService.hasPermission(request, authentication)")
                .anyRequest().authenticated();  //其他的需认证才可访问
    }

}
