package com.xh.security.properties;

import com.xh.security.properties.oauth2.OAuth2Properties;
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

    /** 配置放行的访问路径路径 , 号分隔*/
    private String permitUrls;

    /** OAuth2登录认证配置*/
    private OAuth2Properties oauth2 = new OAuth2Properties();
}
