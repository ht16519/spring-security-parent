package com.xh.security.core.properties;

import com.xh.security.core.consts.URLConst;
import com.xh.security.core.properties.oauth2.OAuth2Properties;
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

    /** 第三方OAuth2登录认证配置*/
    private OAuth2Properties oauth2 = new OAuth2Properties();

    /** 系统内部OAuth2.0支持配置*/
    private OAuth2SupportProperties oauth2Support = new OAuth2SupportProperties();

    /**登出路径*/
    private String logoutUrl = URLConst.LOGOUT_PATH;
}
