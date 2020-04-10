package com.xh.security.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @Name ValidateCodeProperties
 * @Description 验证码配置类
 * @Author wen
 * @Date 2020-04-09
 */
@Getter
@Setter
public class ValidateCodeProperties {

    private ImageCodeProperties image = new ImageCodeProperties();

}
