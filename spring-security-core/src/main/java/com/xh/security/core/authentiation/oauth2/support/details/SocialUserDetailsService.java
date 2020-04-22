package com.xh.security.core.authentiation.oauth2.support.details;

import com.xh.security.core.authentiation.oauth2.support.model.AuthUser;
import com.xh.security.core.authentiation.oauth2.support.model.SocialUserDetails;

/**
 * @Name UserDetails4MobileService
 * @Description OAuth2.0登录服务类
 * @Author wen
 * @Date 2020-04-10
 */
public interface SocialUserDetailsService {

    /**
     * 通过提供商唯一凭证查询用户
     */
    default SocialUserDetails loadUserByProviderId(String providerId, String source) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param authUser 提供从第三方获取到的用户信息
     * @return UserDetails 返回注册成功后的用户信息
     * @Name silenceRegister
     * @Description 静默注册方法
     */
    default SocialUserDetails silenceRegister(AuthUser authUser) {
        throw new UnsupportedOperationException();
    }

}
