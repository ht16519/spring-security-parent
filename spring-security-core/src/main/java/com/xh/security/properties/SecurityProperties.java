package com.xh.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Name BrowserProperties
 * @Description 全局安全属性配置类
 * @Author wen
 * @Date 2020-04-09
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "xh.security")
public class SecurityProperties {

    /** PC端安全属性配置类*/
    private BrowserProperties browser = new BrowserProperties();

    /** 验证码配置类*/
    private ValidateCodeProperties code = new ValidateCodeProperties();
}
