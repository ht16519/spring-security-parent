package com.xh.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xh.security.enums.LoginEnum;
import com.xh.security.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

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
@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;
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
            //返回json报文
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication.getPrincipal()));
            response.getWriter().flush();
            response.getWriter().close();
        }else {
            super.onAuthenticationSuccess(request, response, authentication);   //页面跳转
        }
    }

}
