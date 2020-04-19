package com.xh.security.core.utils;

import com.xh.security.core.exception.ValidateCodeException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Name ValidateCodeUtil
 * @Description 验证码key工具类
 * @Author wen
 * @Date 2020-04-18
 */
public class ValidateCodeUtil {

    private final static String cookieName = "VALIDATECODE";

    private final static String ONLY_DEVICEID = "deviceId";

    private final static int timeout = 60 * 10;

    public static String getKey(String key, HttpServletRequest request) {
        String id = request.getHeader(ONLY_DEVICEID);
        if (StringUtils.isNotBlank(id) || StringUtils.isNotBlank(id = request.getRequestedSessionId())) {
            return key + id;
        }
        Cookie cookie = CookieUtil.get(cookieName, request);
        if (cookie == null) {
            throw new ValidateCodeException("验证码不存在，请重新获取验证码");
        }
        return key + cookie.getValue();
    }

    public static String setKey(String key, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getHeader(ONLY_DEVICEID);
        String cookieValue;
        if (StringUtils.isNotBlank(id) || StringUtils.isNotBlank(id = request.getRequestedSessionId())) {
            return key + id;
        }
        Cookie cookie = CookieUtil.get(cookieName, request);
        if (cookie != null) {
            cookieValue = cookie.getValue();
            if (StringUtils.isBlank(cookieValue)) {
                cookieValue = RandomStringUtils.randomNumeric(32);
            }
            CookieUtil.set(response, cookieName, cookieValue, timeout);
        } else {
            cookieValue = RandomStringUtils.randomNumeric(32);
            CookieUtil.set(response, cookieName, cookieValue, timeout);
        }
        return key + cookieValue;
    }

}
