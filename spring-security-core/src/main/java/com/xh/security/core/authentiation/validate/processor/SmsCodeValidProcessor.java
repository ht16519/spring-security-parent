package com.xh.security.core.authentiation.validate.processor;

import com.xh.security.core.consts.KeyConst;
import org.springframework.stereotype.Component;

/**
 * @Name ValidSmsCodeProcessor
 * @Description 短信验证码验证处理器
 * @Author wen
 * @Date 2020-04-10
 */
@Component(KeyConst.SMS_CODE_VALID_PROCESSOR_BEAN_NAME)
public class SmsCodeValidProcessor extends AbstractValidateCodeProcessor {

    public SmsCodeValidProcessor() {
        super.setInputCodeKey("smsCode");
        super.setCacheCodeKey(KeyConst.CACHE_MOBILE_CODE_KEY);
    }

}
