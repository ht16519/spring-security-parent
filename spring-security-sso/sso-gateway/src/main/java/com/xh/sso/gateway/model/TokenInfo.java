package com.xh.sso.gateway.model;

import lombok.*;


/**
 * @Name TokenInfo
 * @Description 令牌
 * @Author wen
 * @Date 2020-04-24
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {

    /** 是否可用*/
    private boolean active;

    /** clientId*/
    private String client_id;

    /** 作用范围*/
    private String[] scope;

    /** 用户id*/
    private Long id;

    /** 发放的用户名*/
    private String user_name;

    /** 允许访问的资源服务器*/
    private String[] aud;

    /** 失效时间*/
    private long exp;

    /** 权限*/
    private String[] authorities;
}
