package com.xh.security.browser.controller;

import com.xh.security.core.authentiation.oauth2.support.enums.AuthResponseStatus;
import com.xh.security.core.authentiation.oauth2.support.model.AuthResponse;
import com.xh.security.core.consts.URLConst;
import com.xh.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Name BrowserSecurityController
 * @Description
 * @Author wen
 * @Date 2020-04-09
 */
@Slf4j
@RestController
public class BrowserSecurityController {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * @Name requireAuthentication
     * @Description 表单登录认证逻辑处理（当需要认证时跳转到这里）
     * @Author wen
     * @Date 2020/4/9
     */
    @GetMapping(URLConst.REQUIRE_AUTHENTICATION_PATH)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public AuthResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取引发登录认证跳转的请求
        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
        if (null != savedRequest) {
            String redirectUrl = savedRequest.getRedirectUrl();
            log.info("【登录认证服务】引发认证跳转请求的路径:{}", redirectUrl);
            if (StringUtils.endsWithIgnoreCase(redirectUrl, ".html")) {
                //判断请求以.html结尾，则跳转到指定登录页
                response.sendRedirect(securityProperties.getBrowser().getLoginPage());
            }
        }
        return AuthResponse.build(AuthResponseStatus.UNAUTHORIZED);
    }

}
