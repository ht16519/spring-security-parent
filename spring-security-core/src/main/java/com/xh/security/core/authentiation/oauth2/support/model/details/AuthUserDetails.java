package com.xh.security.core.authentiation.oauth2.support.model.details;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Name AuthUserDetails
 * @Description 登录认证的UserDetails
 *
 * 子类实现时，请务必实现以下方法：
 *     @Override
 *     public void eraseCredentials() {
 *         password = null;
 *     }
 *
 *     @Override
 *     public String toString() {
 *         return this.username;
 *     }
 *
 *     @Override
 *     public int hashCode() {
 *         return username.hashCode();
 *     }
 *
 *     @Override
 *     public boolean equals(Object obj) {
 *         return this.toString().equals(obj.toString());
 *     }
 *
 *     @Override
 *     public Integer getUserId() {
 *         return this.getId();
 *     }
 *
 *     否则：
 *     1.内置的同用户多session登录验证不会生效，
 *     2.内置的用户绑定第三方社交登录功能不会生效，
 *     3.认证后的Authentication对象将带有用户密码信息（敏感信息应该屏蔽）
 */
public interface AuthUserDetails<T> extends UserDetails, CredentialsContainer {

    /** 用户唯一值（一般为用户id字段值）*/
    T getUserId();

}