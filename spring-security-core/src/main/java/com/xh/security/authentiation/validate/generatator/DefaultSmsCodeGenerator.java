/**
 *
 */
package com.xh.security.authentiation.validate.generatator;

import com.xh.security.properties.SecurityProperties;
import com.xh.security.authentiation.validate.code.ValidateCode;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name SimpleCodeGenerator
 * @Description 简单的短信验证码生成器
 * @Author wen
 * @Date 2020/4/9
 */
@Setter
public class DefaultSmsCodeGenerator implements ValidateCodeGenerator {

    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(HttpServletRequest request) {
        int len = securityProperties.getCode().getSms().getLength();
        if(4 > len){
            len = 4;
        }
        return ValidateCode.build(RandomStringUtils.randomNumeric(len), securityProperties.getCode().getImage().getExpireIn());
    }

}
