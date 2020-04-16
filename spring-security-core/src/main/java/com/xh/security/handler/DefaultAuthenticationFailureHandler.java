package com.xh.security.handler;

import com.xh.security.authentiation.oauth2.support.enums.AuthResponseStatus;
import com.xh.security.authentiation.oauth2.support.model.AuthResponse;
import com.xh.security.enums.LoginEnum;
import com.xh.security.exception.ValidateCodeException;
import com.xh.security.properties.SecurityProperties;
import com.xh.security.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

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
public class DefaultAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private SecurityProperties securityProperties;

    public DefaultAuthenticationFailureHandler(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    /**
     * @Name onAuthenticationSuccess
     * @Description 该方法登录失败会被调用
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String errorMessage;
        if (e instanceof ValidateCodeException || e instanceof UsernameNotFoundException) {
            errorMessage = e.getMessage();
        } else if (e instanceof BadCredentialsException) {
            errorMessage = "用户名或者密码不正确";
        } else if (e instanceof LockedException) {
            errorMessage = "该用户已被冻结，请联系管理员";
        } else {
            errorMessage = "登录失败";
        }
        e.getStackTrace();
        log.info("登录失败:{}", errorMessage);
        if (LoginEnum.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            ResponseUtil.writer(new AuthResponse(AuthResponseStatus.FAILURE.getCode(), errorMessage), response);
        } else {
            request.setAttribute("msg", errorMessage);      //设置登录失败错误信息
            super.setDefaultFailureUrl(securityProperties.getBrowser().getLoginPage());
            super.onAuthenticationFailure(request, response, e);
        }
    }


}
