package com.xh.security.validate.generatator;

import com.xh.security.validate.code.ImageCode;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name ImageCodeGenerator
 * @Description
 * @Author wen
 * @Date 2020-04-10
 */
public interface ImageCodeGenerator {
    
    /**
    * @Name generate
    * @Description 生成验证码
    * @Author wen
    * @Date 2020/4/10
    * @param request
    * @return com.xh.security.validate.code.ImageCode 
    */
    ImageCode generate(HttpServletRequest request);
    
}
