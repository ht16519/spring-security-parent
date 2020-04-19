package com.xh.security.core.properties;

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
public class SmsCodeProperties {

    /** 验证码长度*/
    private int length = 6;

    /** 缓存失效时间（默认5分钟）*/
    private int expireIn = 60 * 5;

    /** 短信验证码校验路径*/
    private String urls;

}
