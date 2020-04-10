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
public class ImageCodeProperties {

    private int width = 67;

    private int height = 23;

    private int length = 4;

    private int expireIn = 60;

    private String urls;
}
