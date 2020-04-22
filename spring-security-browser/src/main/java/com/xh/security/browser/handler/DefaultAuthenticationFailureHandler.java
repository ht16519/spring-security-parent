package com.xh.security.browser.handler;

import com.xh.security.core.authentiation.oauth2.support.enums.AuthResponseStatus;
import com.xh.security.core.authentiation.oauth2.support.model.AuthResponse;
import com.xh.security.core.enums.LoginEnum;
import com.xh.security.core.properties.SecurityProperties;
import com.xh.security.core.utils.ExceptionMessage;
import com.xh.security.core.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
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
        String errorMessage = ExceptionMessage.convert(e);
        e.getStackTrace();
        log.info("登录失败:{}", errorMessage);
        if (LoginEnum.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            ResponseUtil.write(new AuthResponse(AuthResponseStatus.FAILURE.getCode(), errorMessage), response);
        } else {
            request.setAttribute("msg", errorMessage);      //设置登录失败错误信息
            super.setDefaultFailureUrl(securityProperties.getBrowser().getLoginPage());
            super.onAuthenticationFailure(request, response, e);
        }
    }


}
