package com.xh.security.core.authentiation.validate.processor;

import com.xh.security.core.consts.BeanNameConst;
import org.springframework.stereotype.Component;

/**
 * @Name ValidSmsCodeProcessor
 * @Description 短信验证码验证处理器
 * @Author wen
 * @Date 2020-04-10
 */
@Component(BeanNameConst.SMS_CODE_VALID_PROCESSOR_BEAN_NAME)
public class SmsCodeValidProcessor extends AbstractValidateCodeProcessor {

    public SmsCodeValidProcessor() {
        super.setInputCodeKey("smsCode");
        super.setCacheCodeKey(BeanNameConst.CACHE_MOBILE_CODE_KEY);
    }

}
