package com.xh.security.authentiation.mobile;

/**
 * @Name MobileValidator
 * @Description
 * @Author wen
 * @Date 2020-04-12
 */
public class MobileValidator {

    public static boolean isMobile(String mobile){
        return mobile.matches("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$");
    }
}
