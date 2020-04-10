package com.xh.security.validate.code;

import lombok.AllArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class ImageCode {

    private BufferedImage image;

    private String code;

    private LocalDateTime expireTime;

    public static ImageCode build(BufferedImage image, String code, int expireIn) {
        return new ImageCode(image, code, LocalDateTime.now().plusSeconds(expireIn));
    }

    public boolean isExpire(){
        return LocalDateTime.now().isAfter(this.expireTime);
    }

}
