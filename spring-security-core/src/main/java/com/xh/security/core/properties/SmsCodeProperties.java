package com.xh.security.core.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @Name ValidateCodeProperties
 * @Description 短信验证码配置类
 * @Author wen
 * @Date 2020-04-09
 */
@Getter
@Setter
public class SmsCodeProperties {

    private int length = 6;

    private int expireIn = 60 * 5;

    private String urls;

}
