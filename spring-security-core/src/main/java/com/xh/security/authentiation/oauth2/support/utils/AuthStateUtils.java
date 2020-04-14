package com.xh.security.authentiation.oauth2.support.utils;

import com.xh.security.consts.CommonUtil;

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
        return type + CommonUtil.COLON + UuidUtils.getUUID();
    }
}
