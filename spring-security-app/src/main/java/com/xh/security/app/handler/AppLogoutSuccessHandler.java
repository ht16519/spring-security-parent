package com.xh.security.app.handler;

import com.xh.security.core.authentiation.oauth2.support.enums.AuthResponseStatus;
import com.xh.security.core.authentiation.oauth2.support.model.AuthResponse;
import com.xh.security.core.properties.SecurityProperties;
import com.xh.security.core.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Name DefaultLogoutSuccessHandler
 * @Description 默认登出成功处理器
 * @Author wen
 * @Date 2020-04-16
 */
@Slf4j
public class AppLogoutSuccessHandler implements LogoutSuccessHandler {

    private SecurityProperties securityProperties;

    public AppLogoutSuccessHandler(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("登出成功...");
        ResponseUtil.writer(AuthResponse.build(AuthResponseStatus.SUCCESS, "登出成功"), response);
    }

}
