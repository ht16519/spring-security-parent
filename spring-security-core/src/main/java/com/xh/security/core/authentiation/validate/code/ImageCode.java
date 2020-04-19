package com.xh.security.core.authentiation.validate.code;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @Name ImageCode
 * @Description 图形验证码
 * @Author wen
 * @Date 2020-04-09
 */
@Getter
@Setter
public class ImageCode extends ValidateCode {

    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, long expireTime) {
        super(code, expireTime);
        this.image = image;
    }

    public static ImageCode build(BufferedImage image, String code, long expireTime){
        return new ImageCode(image, code, expireTime);
    }

}
