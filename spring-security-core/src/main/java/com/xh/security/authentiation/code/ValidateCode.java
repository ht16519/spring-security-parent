package com.xh.security.authentiation.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class ValidateCode {

    private String code;

    private LocalDateTime expireTime;

    public static ValidateCode build(String code, int expireIn) {
        return new ValidateCode(code, LocalDateTime.now().plusSeconds(expireIn));
    }

    public boolean isExpire(){
        return LocalDateTime.now().isAfter(this.expireTime);
    }

}
