package com.xh.security.core.authentiation.validate.code;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Name ValidateCode
 * @Description 验证码
 * @Author wen
 * @Date 2020-04-10
 */
@Getter
@Setter
@NoArgsConstructor
public class ValidateCode {

    private String code;

    /** 超时时间（单位：毫秒）*/
    private long expireTime;

    public ValidateCode(String code, long expireTime) {
        this.code = code;
        this.expireTime = System.currentTimeMillis() + expireTime * 1000;
    }

    /**
     * @Name build
     * @Description 构建验证码
     * @Author wen
     * @Date 2020/4/18
     * @param code（验证码）
     * @param expireTime（超时时间（单位：秒））
    */
    public static ValidateCode build(String code, long expireTime) {
        return new ValidateCode(code, expireTime);
    }

    public boolean expire(){
        return System.currentTimeMillis() > this.expireTime;
    }

}
