package com.xh.security.authentiation.oauth2.support.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xh.security.authentiation.oauth2.support.enums.AuthResponseStatus;
import lombok.*;

import java.io.Serializable;

/**
 * JustAuth统一授权响应类
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @since 1.8
 */
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthResponse<T> implements Serializable {
    /**
     * 授权响应状态码
     */
    private int code;

    /**
     * 授权响应信息
     */
    private String msg;

    /**
     * 授权响应数据，当且仅当 code = 2000 时返回
     */
    private T data;

    public AuthResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public AuthResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> AuthResponse build(AuthResponseStatus status, T data) {
        return new AuthResponse<>(status.getCode(), status.getMsg(), data);
    }

    public static AuthResponse build(AuthResponseStatus status) {
        return new AuthResponse<>(status.getCode(), status.getMsg());
    }

    public static AuthResponse failure() {
        return build(AuthResponseStatus.FAILURE);
    }

    public static <T> AuthResponse success(T data) {
        return build(AuthResponseStatus.SUCCESS, data);
    }


    /**
     * 是否请求成功
     *
     * @return true or false
     */
    public boolean ok() {
        return this.code == AuthResponseStatus.SUCCESS.getCode();
    }
}
