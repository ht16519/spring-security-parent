package com.xh.security.core.authentiation.oauth2.support.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * JustAuth通用的状态码对照表
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @since 1.8
 */
@Getter
@AllArgsConstructor
public enum AuthResponseStatus {
    /**
     * 2000：正常；
     * other：调用异常，具体异常内容见{@code msg}
     */
    SUCCESS(200, "Success"),
    UNAUTHORIZED(401, "访问的服务需要身份认证，请引导用户到登陆页面"),
    FAILURE(500, "Failure"),
    NOT_IMPLEMENTED(501, "Not Implemented"),
    PARAMETER_INCOMPLETE(502, "Parameter incomplete"),
    UNSUPPORTED(503, "Unsupported operation"),
    NO_AUTH_SOURCE(504, "AuthDefaultSource cannot be null"),
    UNIDENTIFIED_PLATFORM(505, "Unidentified platform"),
    ILLEGAL_REDIRECT_URI(506, "Illegal redirect uri"),
    ILLEGAL_REQUEST(507, "Illegal request"),
    ILLEGAL_CODE(508, "Illegal code"),
    ILLEGAL_STATUS(509, "Illegal state"),
    REQUIRED_REFRESH_TOKEN(510, "The refresh token is required; it must not be null"),

    INVALID_PARAMETER(4000, "参数不合法"),
    INVALID_EMPTY_MONBLE_PARAMETER(4001, "请输入手机号码"),
    INVALID_MOBILE_PARAMETER(4002, "手机号码不正确"),


    ;

    private int code;
    private String msg;
}

