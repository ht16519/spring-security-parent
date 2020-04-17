package com.xh.security.core.authentiation.validate.processor;

import com.xh.security.core.exception.ValidateCodeException;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name ValidateCodeProcessorHolder
 * @Description 验证码处理器接口
 * @Author wen
 * @Date 2020-04-10
 */
public interface ValidateCodeProcessor {

    void validate(HttpServletRequest request) throws ValidateCodeException;

}
