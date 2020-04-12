package com.xh.security.config;

import com.xh.security.authentiation.config.SmsCodeAuthenticationSecurityConfig;
import com.xh.security.authentiation.generatator.DefaultImageCodeGenerator;
import com.xh.security.authentiation.generatator.DefaultSmsCodeGenerator;
import com.xh.security.authentiation.generatator.ValidateCodeGenerator;
import com.xh.security.authentiation.sms.DefaultSmsCodeSender;
import com.xh.security.authentiation.sms.SmsCodeSender;
import com.xh.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Name BeanConfig
 * @Description
 * @Author wen
 * @Date 2020-04-10
 */
@Configuration
public class CoreBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /** 简单图形验证码生成器*/
    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
        DefaultImageCodeGenerator imageCodeGenerator = new DefaultImageCodeGenerator();
        imageCodeGenerator.setSecurityProperties(securityProperties);
        return imageCodeGenerator;
    }

    /** 简单短信验证码生成器*/
    @Bean
    @ConditionalOnMissingBean(name = "smsCodeGenerator")
    public ValidateCodeGenerator smsCodeGenerator() {
        DefaultSmsCodeGenerator smsCodeGenerator = new DefaultSmsCodeGenerator();
        smsCodeGenerator.setSecurityProperties(securityProperties);
        return smsCodeGenerator;
    }

    /** 短信验证码发送器*/
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }

    /** 短信验证码认证的安全配置类*/
    @Bean
    public SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig() {
        return new SmsCodeAuthenticationSecurityConfig();
    }

}
