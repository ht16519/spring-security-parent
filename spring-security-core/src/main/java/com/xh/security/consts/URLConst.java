package com.xh.security.consts;

/**
 * @Name URLConst
 * @Description 路径常量类
 * @Author wen
 * @Date 2020-04-09
 */
public interface URLConst {

    /** 简单的自定义登录页面路径*/
    String SIMPLE_SIGNIN_PAGE_PATH = "/simple-signIn-page.html";

    /** 处理表单登录的请求路径*/
    String AUTHENTICATION_FORM_PATH = "/authentication/form";

    /** 处理手机登录的请求路径*/
    String AUTHENTICATION_MOBILE_PATH = "/authentication/mobile";

    /** 认证处理请求路径*/
    String REQUIRE_AUTHENTICATION_PATH = "/authentication/require";

    /** 图形验证码请求路径*/
    String VALIDATE_IMAGE_CODE_PATH = "/code/image";

    /** 手机验证码请求路径*/
    String VALIDATE_SMS_CODE_PATH = "/code/sms";
}
