package com.xh.security.authentiation.oauth2.support.model;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Name UserDetails4OAuth2
 * @Description
 * @Author wen
 * @Date 2020-04-14
 */
public interface UserDetails4OAuth2<T> extends UserDetails {

    /** 用户唯一值（一般为用户id字段值）*/
    T getUserId();

}
