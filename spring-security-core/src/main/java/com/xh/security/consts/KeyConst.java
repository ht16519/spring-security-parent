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



}
