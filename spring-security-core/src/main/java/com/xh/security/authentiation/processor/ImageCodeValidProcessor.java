package com.xh.security.authentiation.processor;

import com.xh.security.consts.KeyConst;
import org.springframework.stereotype.Component;

/**
 * @Name ValidSmsCodeProcessor
 * @Description 图片验证码验证处理器
 * @Author wen
 * @Date 2020-04-10
 */
@Component(KeyConst.IMAGE_CODE_VALID_PROCESSOR_BEAN_NAME)
public class ImageCodeValidProcessor extends AbstractValidateCodeProcessor {

    public ImageCodeValidProcessor() {
        super.setInputCodeKey("imageCode");
        super.setCacheCodeKey(KeyConst.CACHE_IMAGE_CODE_KEY);
    }

}
