package com.xh.security.handler;

import com.xh.security.authentiation.oauth2.support.model.AuthResponse;
import com.xh.security.enums.LoginEnum;
import com.xh.security.properties.SecurityProperties;
import com.xh.security.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Name XhAuthenticationSuccessHandler
 * @Description 登录认证成功处理类
 * @Author wen
 * @Date 2020-04-09
 */
@Slf4j
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private SecurityProperties securityProperties;
    /**
    * @Name onAuthenticationSuccess
    * @Description 该方法登录成功会被调用
    */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("登录成功...");
        if(LoginEnum.JSON.equals(securityProperties.getBrowser().getLoginType())){
            ResponseUtil.writer(AuthResponse.success(authentication.getPrincipal()), response);
        }else {
            super.onAuthenticationSuccess(request, response, authentication);   //页面跳转
        }
    }

}
