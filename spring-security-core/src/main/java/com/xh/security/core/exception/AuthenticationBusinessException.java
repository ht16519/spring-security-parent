package com.xh.security.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Name ValidateCodeException
 * @Description 自定义认证业务异常
 * @Author wen
 * @Date 2020-04-09
 */
public class AuthenticationBusinessException extends AuthenticationException {

    private static final long serialVersionUID = 5959202567885452442L;

    public AuthenticationBusinessException(String msg) {
        super(msg);
    }
}
