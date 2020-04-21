package com.xh.security.core.authorize;

import com.xh.security.core.consts.URLConst;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @Name SecurityCoreAuthorizeConfigProvider
 * @Description 内置授权配置提供者
 * @Author wen
 * @Date 2020-04-21
 */
@Component
@Order(Integer.MIN_VALUE)   //自定义的授权配置提供者加载顺序应该最小（优先加载）
public class SecurityCoreAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(
                URLConst.SIMPLE_SIGNIN_PAGE_PATH,
                URLConst.REQUIRE_AUTHENTICATION_PATH,               //放行自定义登录认证请求处理Controller路径
                URLConst.VALIDATE_IMAGE_CODE_PATH,                  //放行图片验证码生成路径
                URLConst.VALIDATE_SMS_CODE_PATH,                    //放行短信验证码生成路径
                URLConst.HANDLE_SESSION_INVALID_URL,                //放行处理session失效地址
                URLConst.LOGOUT_PATH,                               //放行登出默认路径
                "/static/**",                                       //放行静态资源
                "/oauth2/**").permitAll();
    }
}
