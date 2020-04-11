package com.xh.security.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @Name ValidateCodeProperties
 * @Description 验证码配置类
 * @Author wen
 * @Date 2020-04-09
 */
@Getter
@Setter
public class ValidateCodeProperties {

    /** 图形验证码配置*/
    private ImageCodeProperties image = new ImageCodeProperties();

    /** 短信验证码配置*/
    private SmsCodeProperties sms = new SmsCodeProperties();

}
