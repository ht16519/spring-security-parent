package com.xh.security.authentiation.generatator;

import com.xh.security.authentiation.code.ValidateCode;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name ImageCodeGenerator
 * @Description
 * @Author wen
 * @Date 2020-04-10
 */
public interface ValidateCodeGenerator {
    
    /**
    * @Name generate
    * @Description 生成验证码
    * @Author wen
    * @Date 2020/4/10
    * @param request
    * @return com.xh.security.validate.code.ImageCode 
    */
    ValidateCode generate(HttpServletRequest request);
    
}
