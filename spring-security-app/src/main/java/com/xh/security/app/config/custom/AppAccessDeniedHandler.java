package com.xh.security.app.config.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Name AppAccessDeniedHandler
 * @Description 访问拒绝403处理器（没有权限访问）
 * @Author wen
 * @Date 2020-04-25
 */
@Slf4j
public class AppAccessDeniedHandler extends OAuth2AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
        log.info("403，访问被拒绝");
        super.handle(request, response, authException);
    }
}
