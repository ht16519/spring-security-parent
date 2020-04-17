/**
 *
 */
package com.xh.security.core.authentiation.validate.generatator;

import com.xh.security.core.properties.SecurityProperties;
import com.xh.security.core.authentiation.validate.code.ValidateCode;
import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name SimpleCodeGenerator
 * @Description 简单的短信验证码生成器
 * @Author wen
 * @Date 2020/4/9
 */
public class DefaultSmsCodeGenerator implements ValidateCodeGenerator {

    private SecurityProperties securityProperties;

    public DefaultSmsCodeGenerator(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public ValidateCode generate(HttpServletRequest request) {
        int len = securityProperties.getCode().getSms().getLength();
        if(4 > len){
            len = 4;
        }
        return ValidateCode.build(RandomStringUtils.randomNumeric(len), securityProperties.getCode().getImage().getExpireIn());
    }

}
