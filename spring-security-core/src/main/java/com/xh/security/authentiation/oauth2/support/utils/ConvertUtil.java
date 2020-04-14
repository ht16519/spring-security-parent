package com.xh.security.authentiation.oauth2.support.utils;

import com.xh.security.authentiation.oauth2.support.model.AuthCallback;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name Convert
 * @Description 数据转换工具类
 * @Author wen
 * @Date 2020-04-13
 */
public class ConvertUtil {

    public static AuthCallback toAuthCallback(HttpServletRequest request){
        return AuthCallback.builder()
                .code(request.getParameter("code"))
                .auth_code(request.getParameter("auth_code"))
                .state(request.getParameter("state"))
                .authorization_code(request.getParameter("authorization_code"))
                .oauthToken(request.getParameter("oauthToken"))
                .oauthVerifier(request.getParameter("oauthVerifier")).build();
    }
}
