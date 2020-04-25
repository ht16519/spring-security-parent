package com.xh.security.app.config.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Name AppAuthenticationEntryPoint
 * @Description 访问401处理器（令牌错误 或者 未携带令牌 都将经过该处理器）
 * @Author wen
 * @Date 2020-04-25
 */
@Slf4j
public class AppAuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if(authException instanceof AccessTokenRequiredException){
            log.debug("匿名请求，未携带token令牌");
            log.info("【日志审计服务】update log to 401");
        }else {
            log.debug("无效的token令牌（令牌错误）");
            log.info("【日志审计服务】add log 401");
        }
        super.commence(request, response, authException);
    }
}
