package com.xh.security.core.authentiation.oauth2.support.utils;

import com.xh.security.core.consts.CommonConst;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * AuthState工具类，默认只提供一个创建随机uuid的方法
 */
public class AuthStateUtils {

    /**
     * 生成对应授权平台类型的随机state
     *
     * @param type
     * @return 随机的state字符串
     */
    public static String createState(String type) {
        return type + CommonConst.COLON + UuidUtils.getUUID();
    }

    /** 生成对应授权平台类型的随机state*/
    public static String createState(String type, String id) {
        return type + CommonConst.COLON + RandomStringUtils.randomAlphanumeric(16) + CommonConst.COLON  + id;
    }

}
