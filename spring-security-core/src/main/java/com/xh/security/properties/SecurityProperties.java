package com.xh.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Name BrowserProperties
 * @Description 全局安全属性配置类
 * @Author wen
 * @Date 2020-04-09
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "xh.security")
public class SecurityProperties {

    private BrowserProperties browser;
}
