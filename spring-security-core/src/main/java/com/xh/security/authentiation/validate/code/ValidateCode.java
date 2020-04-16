package com.xh.security.authentiation.validate.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Name SmsCode
 * @Description 短信验证码
 * @Author wen
 * @Date 2020-04-10
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCode implements Serializable {

    private static final long serialVersionUID = 6160591069915127038L;

    private String code;

    private LocalDateTime expireTime;

    public static ValidateCode build(String code, int expireIn) {
        return new ValidateCode(code, LocalDateTime.now().plusSeconds(expireIn));
    }

    public static ValidateCode build(String code, LocalDateTime plusSeconds) {
        return new ValidateCode(code, plusSeconds);
    }

    public boolean isExpire(){
        return LocalDateTime.now().isAfter(this.expireTime);
    }

}
