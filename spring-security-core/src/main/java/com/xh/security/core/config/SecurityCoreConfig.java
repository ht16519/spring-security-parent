package com.xh.security.core.config;

import com.xh.security.core.authentiation.oauth2.config.OAuth2AuthenticationSecurityConfig;
import com.xh.security.core.utils.cache.AuthCache;
import com.xh.security.core.utils.cache.AuthDefaultCache;
import com.xh.security.core.utils.cache.AuthRedisCache;
import com.xh.security.core.authentiation.validate.config.SmsCodeAuthenticationSecurityConfig;
import com.xh.security.core.authentiation.validate.config.ValidateCodeSecurityConfig;
import com.xh.security.core.authentiation.validate.generatator.DefaultImageCodeGenerator;
import com.xh.security.core.authentiation.validate.generatator.DefaultSmsCodeGenerator;
import com.xh.security.core.authentiation.validate.generatator.ValidateCodeGenerator;
import com.xh.security.core.authentiation.validate.sms.DefaultSmsCodeSender;
import com.xh.security.core.authentiation.validate.sms.SmsCodeSender;
import com.xh.security.core.consts.BeanNameConst;
import com.xh.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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

    @Autowired
//    @Qualifier(BeanNameConst.APP_AUTHENTICATION_SUCCESS_HANDLER_BEAN_NAME)
    private AuthenticationSuccessHandler successHandler;
    @Autowired
//    @Qualifier(BeanNameConst.APP_AUTHENTICATION_FAILURE_HANDLER_BEAN_NAME)
    private AuthenticationFailureHandler failureHandler;

    /**
     * 自定义密码加密器
     */
    @Bean
//    @Bean(BeanNameConst.CUSTOM_PASSWORD_ENCODER_BEAN_NAME)
//    @ConditionalOnMissingBean(name = BeanNameConst.CUSTOM_PASSWORD_ENCODER_BEAN_NAME)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 简单图形验证码生成器
     */
    @Bean(BeanNameConst.IMAGE_CODE_GENERATOR_BEAN_NAME)
    @ConditionalOnMissingBean(name = BeanNameConst.IMAGE_CODE_GENERATOR_BEAN_NAME)
    public ValidateCodeGenerator imageCodeGenerator() {
        return new DefaultImageCodeGenerator(securityProperties);
    }

    /**
     * 简单短信验证码生成器
     */
    @Bean(BeanNameConst.SMS_CODE_GENERATOR_BEAN_NAME)
    @ConditionalOnMissingBean(name = BeanNameConst.SMS_CODE_GENERATOR_BEAN_NAME)
    public ValidateCodeGenerator smsCodeGenerator() {
        return new DefaultSmsCodeGenerator(securityProperties);
    }

    /**
     * 短信验证码发送器
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }

    /**
     * 短信验证码认证的安全配置类
     */
    @Bean
    public SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig() {
        return new SmsCodeAuthenticationSecurityConfig(successHandler, failureHandler);
    }

    /**
     * OAuth2.0认证的安全配置类
     */
    @Bean
    @ConditionalOnExpression("'${xh.security.oauth2.siteDomain}'.matches('^(http|https)://.*')")
    public OAuth2AuthenticationSecurityConfig oauth2AuthenticationSecurityConfig() {
        return new OAuth2AuthenticationSecurityConfig(successHandler, failureHandler);
    }

    /**
     * 配置基于Redis的缓存器
     */
    @Bean
    @ConditionalOnExpression("'${spring.session.storeType}'.equalsIgnoreCase('redis')")
    public AuthCache authRedisCache(StringRedisTemplate stringRedisTemplate) {
        return new AuthRedisCache(stringRedisTemplate);
    }

    /**
     * 配置默认的缓存器
     */
    @Bean
    @ConditionalOnMissingBean(name = "authRedisCache")
    public AuthCache authDefaultCache() {
        return new AuthDefaultCache();
    }

    /**
     * 短信验证码认证的安全配置类
     */
    @Bean
    public ValidateCodeSecurityConfig validateCodeSecurityConfig() {
        return new ValidateCodeSecurityConfig(failureHandler);
    }

}
