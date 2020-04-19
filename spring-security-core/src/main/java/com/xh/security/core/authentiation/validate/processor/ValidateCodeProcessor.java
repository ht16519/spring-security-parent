package com.xh.security.core.authentiation.validate.processor;

import com.xh.security.core.exception.AuthenticationBusinessException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Name ValidateCodeProcessorHolder
 * @Description 验证码处理器接口
 * @Author wen
 * @Date 2020-04-10
 */
public interface ValidateCodeProcessor {

    void validate(HttpServletRequest request, HttpServletResponse response) throws AuthenticationBusinessException;

}
