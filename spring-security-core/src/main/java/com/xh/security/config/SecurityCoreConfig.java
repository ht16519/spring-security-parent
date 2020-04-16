package com.xh.security.config;

import com.xh.security.authentiation.oauth2.config.OAuth2AuthenticationSecurityConfig;
import com.xh.security.authentiation.validate.config.SmsCodeAuthenticationSecurityConfig;
import com.xh.security.authentiation.validate.generatator.DefaultImageCodeGenerator;
import com.xh.security.authentiation.validate.generatator.DefaultSmsCodeGenerator;
import com.xh.security.authentiation.validate.generatator.ValidateCodeGenerator;
import com.xh.security.authentiation.validate.sms.DefaultSmsCodeSender;
import com.xh.security.authentiation.validate.sms.SmsCodeSender;
import com.xh.security.consts.KeyConst;
import com.xh.security.handler.DefaultAuthenticationFailureHandler;
import com.xh.security.handler.DefaultAuthenticationSuccessHandler;
import com.xh.security.handler.DefaultLogoutSuccessHandler;
import com.xh.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @Name SecurityCoreConfig
 * @Description 安全公共核心配置类
 * @Author wen
 * @Date 2020-04-10
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /** 自定义密码加密器*/
    @Bean(KeyConst.CUSTOM_PASSWORD_ENCODER_BEAN_NAME)
    @ConditionalOnMissingBean(name = KeyConst.CUSTOM_PASSWORD_ENCODER_BEAN_NAME)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** 简单图形验证码生成器*/
    @Bean(KeyConst.IMAGE_CODE_GENERATOR_BEAN_NAME)
    @ConditionalOnMissingBean(name = KeyConst.IMAGE_CODE_GENERATOR_BEAN_NAME)
    public ValidateCodeGenerator imageCodeGenerator() {
        return new DefaultImageCodeGenerator(securityProperties);
    }

    /** 简单短信验证码生成器*/
    @Bean(KeyConst.SMS_CODE_GENERATOR_BEAN_NAME)
    @ConditionalOnMissingBean(name = KeyConst.SMS_CODE_GENERATOR_BEAN_NAME)
    public ValidateCodeGenerator smsCodeGenerator() {
        return new DefaultSmsCodeGenerator(securityProperties);
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

    /** OAuth2.0认证的安全配置类*/
    @Bean
    public OAuth2AuthenticationSecurityConfig oauth2AuthenticationSecurityConfig() {
        return new OAuth2AuthenticationSecurityConfig();
    }

    /** 配置登录成功处理器*/
    @Bean(KeyConst.CUSTOM_AUTHENTICATION_SUCCESS_HANDLER_BEAN_NAME)
    @ConditionalOnMissingBean(name = KeyConst.CUSTOM_AUTHENTICATION_SUCCESS_HANDLER_BEAN_NAME)
    public SavedRequestAwareAuthenticationSuccessHandler successHandler() {
        return new DefaultAuthenticationSuccessHandler(securityProperties);
    }

    /** 配置登录失败处理器*/
    @Bean(KeyConst.CUSTOM_AUTHENTICATION_FAILURE_HANDLER_BEAN_NAME)
    @ConditionalOnMissingBean(name = KeyConst.CUSTOM_AUTHENTICATION_FAILURE_HANDLER_BEAN_NAME)
    public SimpleUrlAuthenticationFailureHandler failureHandler() {
        return new DefaultAuthenticationFailureHandler(securityProperties);
    }

    /** 配置登录失败处理器*/
    @Bean(KeyConst.CUSTOM_LOGOUT_SUCCESS_HANDLER_BEAN_NAME)
    @ConditionalOnMissingBean(name = KeyConst.CUSTOM_LOGOUT_SUCCESS_HANDLER_BEAN_NAME)
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new DefaultLogoutSuccessHandler(securityProperties);
    }
}
