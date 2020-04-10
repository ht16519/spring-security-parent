package com.xh.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xh.security.enums.LoginEnum;
import com.xh.security.exception.ValidateCodeException;
import com.xh.security.properties.SecurityProperties;
import com.xh.security.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Name XhAuthenticationSuccessHandler
 * @Description 登录认证失败处理类
 * @Author wen
 * @Date 2020-04-09
 */
@Slf4j
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * @Name onAuthenticationSuccess
     * @Description 该方法登录失败会被调用
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.info("登录失败...");
        String errorMessage;
        if (e instanceof BadCredentialsException || e instanceof UsernameNotFoundException) {
            errorMessage = "用户名或者密码不正确";
        } else if (e instanceof LockedException) {
            errorMessage = "该用户已被冻结，请联系管理员";
        } else if (e instanceof ValidateCodeException) {
            errorMessage = e.getMessage();
        } else {
            errorMessage = "登录失败";
        }
        if (LoginEnum.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(SimpleResponse.build(errorMessage)));
            response.getWriter().flush();
            response.getWriter().close();
        } else {
            request.setAttribute("msg", errorMessage);      //设置登录失败错误信息
            super.setDefaultFailureUrl(securityProperties.getBrowser().getLoginPage());
            super.onAuthenticationFailure(request, response, e);
        }
    }


}
