package com.xh.security.core.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @Name OAuth2ClientProperties
 * @Description 系统内部OAuth2.0客户端配置
 * @Author wen
 * @Date 2020-04-19
 */
@Getter
@Setter
public class OAuth2ClientProperties {

    /** 配置clientId*/
    private String clientId;

    /** 配置clientSecret*/
    private String clientSecret;

    /** token失效时间（单位：秒，默认：2小时）*/
    private int accessTokenValiditySeconds = 7200;

    /** refreshToken失效时间（单位：秒，默认：3天）*/
    private int refreshTokenValiditySeconds = 259200;

    /** 配置拥有的权限*/
    private String[] scopes;

    /** 配置授权方式*/
    private String[] grantTypes;

    /** 配置授权方式*/
    private String[] redirectUris;

}
