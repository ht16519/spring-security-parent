package com.xh.security.core.authentiation.validate.sms;

/**
 * @Name SmsCodeSender
 * @Description 短信验证码发送器接口
 * @Author wen
 * @Date 2020-04-10
 */
public interface SmsCodeSender {

    /** 发送短信验证码*/
    void send(String mobile, String code);
}
