package com.xh.security.core.authentiation.validate.sms;

import lombok.extern.slf4j.Slf4j;

/**
 * @Name DefaultSmsCodeSender
 * @Description 默认实现
 * @Author wen
 * @Date 2020-04-10
 */
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        log.info("【短信验证码服务】发送短信验证码成功，手机号码：{}，验证码：{}", mobile, code);
    }

}
