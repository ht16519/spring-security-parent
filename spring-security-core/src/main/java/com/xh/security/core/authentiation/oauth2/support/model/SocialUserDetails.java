package com.xh.security.core.authentiation.oauth2.support.model;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Name UserDetails4OAuth2
 * @Description OAuth2.0 用户认证实体
 * @Author wen
 * @Date 2020-04-14
 */
public interface SocialUserDetails<T> extends UserDetails {

    /** 用户唯一值（一般为用户id字段值）*/
    T getUserId();

}
