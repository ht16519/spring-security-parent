package com.xh.security.consts;

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

    /** 手机验证码处理器 bean 名称*/
    String SMS_CODE_VALID_PROCESSOR_BEAN_NAME = "smsCodeValidProcessor";

    /** 图片验证码处理器 bean 名称*/
    String IMAGE_CODE_VALID_PROCESSOR_BEAN_NAME = "imageCodeValidProcessor";

    /** 自定义登陆失败处理器 bean 名称*/
    String CUSTOM_AUTHENTICATION_FAILURE_HANDLER_BEAN_NAME = "customAuthenticationFailureHandler";

    /** 自定义登陆成功处理器 bean 名称*/
    String CUSTOM_AUTHENTICATION_SUCCESS_HANDLER_BEAN_NAME = "customAuthenticationSuccessHandler";

    /** 并发登录时session失效处理 bean 名称*/
    String CONCURRENT_LOGIN_SESSION_INVALID_STRATEGY_BEAN_NAME = "concurrentLoginSessionInvalidStrategy";

    /** session超时失效处理 bean 名称*/
    String TIME_EXPIRED_SESSION_STRATEGY_BEAN_NAME = "timeExpiredSessionStrategy";



}
