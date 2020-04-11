package com.xh.security.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @Name ValidateCodeProperties
 * @Description 图片验证码配置类
 * @Author wen
 * @Date 2020-04-09
 */
@Getter
@Setter
public class ImageCodeProperties extends SmsCodeProperties{

    public ImageCodeProperties() {
        setLength(4);
    }

    private int width = 67;

    private int height = 23;

}
