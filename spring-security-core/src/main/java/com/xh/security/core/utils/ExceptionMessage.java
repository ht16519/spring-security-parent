package com.xh.security.core.utils;

import com.xh.security.core.exception.ValidateCodeException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Name ExceptionMessage
 * @Description
 * @Author wen
 * @Date 2020-04-17
 */
public class ExceptionMessage {

    public static String convert(Exception e) {
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
        return errorMessage;
    }
}
