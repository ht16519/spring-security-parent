package com.xh.security.authentiation.code;

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
public class ImageCode extends ValidateCode {

    private BufferedImage image;

    private String code;

    private LocalDateTime expireTime;

    public ImageCode(BufferedImage image, String code, LocalDateTime plusSeconds) {
        super(code, plusSeconds);
        this.image = image;
    }

    public static ImageCode build(BufferedImage image, String code, int expireIn) {
        return new ImageCode(image, code, LocalDateTime.now().plusSeconds(expireIn));
    }

    public boolean isExpire(){
        return LocalDateTime.now().isAfter(this.expireTime);
    }

}
