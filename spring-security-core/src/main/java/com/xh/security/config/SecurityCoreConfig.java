package com.xh.security.config;

import com.xh.security.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Name SecurityCoreConfig
 * @Description
 * @Author wen
 * @Date 2020-04-09
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

}
