package com.xh.security.core.consts;

/**
 * @Name KeyConst
 * @Description
 * @Author wen
 * @Date 2020-04-09
 */
public interface KeyConst {

    /** session中图形验证码key*/
    String CACHE_IMAGE_CODE_KEY = "validate:code:cache_image_key";

    /** session中手机验证码key*/
    String CACHE_MOBILE_CODE_KEY = "validate:code:cache_mobile_key";

    /** 手机短信验证码生成器 bean 名称*/
    String SMS_CODE_GENERATOR_BEAN_NAME = "smsCodeGenerator";

    /** 图片验证码生成器 bean 名称*/
    String IMAGE_CODE_GENERATOR_BEAN_NAME = "imageCodeGenerator";

    /** 手机短信验证码处理器 bean 名称*/
    String SMS_CODE_VALID_PROCESSOR_BEAN_NAME = "smsCodeValidProcessor";

    /** 图片验证码处理器 bean 名称*/
    String IMAGE_CODE_VALID_PROCESSOR_BEAN_NAME = "imageCodeValidProcessor";

    /** 自定义密码加密器 bean 名称*/
    String CUSTOM_PASSWORD_ENCODER_BEAN_NAME = "customPasswordEncoder";

    /** 自定义登录逻辑类 bean 名称*/
    String CUSTOM_USER_DETAILS_SERVICE_BEAN_NAME = "customUserDetailsService";

    /** 浏览器端登陆失败处理器 bean 名称*/
    String BROWSER_AUTHENTICATION_FAILURE_HANDLER_BEAN_NAME = "browserAuthenticationFailureHandler";

    /** APP端登陆失败处理器 bean 名称*/
    String APP_AUTHENTICATION_FAILURE_HANDLER_BEAN_NAME = "appAuthenticationFailureHandler";

    /** 浏览器端登陆成功处理器 bean 名称*/
    String BORWSER_AUTHENTICATION_SUCCESS_HANDLER_BEAN_NAME = "browserAuthenticationSuccessHandler";

    /** app端登陆成功处理器 bean 名称*/
    String APP_AUTHENTICATION_SUCCESS_HANDLER_BEAN_NAME = "appAuthenticationSuccessHandler";

    /** 浏览器端登出成功处理器 bean 名称*/
    String BROWSER_LOGOUT_SUCCESS_HANDLER_BEAN_NAME = "browserLogoutSuccessHandler";

    /** APP端登出成功处理器 bean 名称*/
    String APP_LOGOUT_SUCCESS_HANDLER_BEAN_NAME = "appLogoutSuccessHandler";

    /** 并发登录时session失效处理 bean 名称*/
    String CONCURRENT_LOGIN_SESSION_INVALID_STRATEGY_BEAN_NAME = "concurrentLoginSessionInvalidStrategy";

    /** session超时失效处理 bean 名称*/
    String TIME_EXPIRED_SESSION_STRATEGY_BEAN_NAME = "timeExpiredSessionStrategy";



}
