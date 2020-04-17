package com.xh.security.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Name ValidateCodeException
 * @Description 自定义验证码校验逻辑异常类
 * @Author wen
 * @Date 2020-04-09
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = 3904731512907962721L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
