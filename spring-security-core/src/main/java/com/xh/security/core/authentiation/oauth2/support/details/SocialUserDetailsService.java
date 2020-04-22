package com.xh.security.core.authentiation.oauth2.support.details;

import com.xh.security.core.authentiation.oauth2.support.model.AuthUser;
import com.xh.security.core.authentiation.oauth2.support.model.details.AuthUserDetails;

/**
 * @Name UserDetails4MobileService
 * @Description OAuth2.0登录服务类
 * @Author wen
 * @Date 2020-04-10
 */
public interface SocialUserDetailsService {

    /**
     * 通过提供商唯一凭证查询用户
     * @param providerId 第三方提供的唯一凭证
     * @param source 第三方应该来源
     */
    default AuthUserDetails loadUserByProviderId(String providerId, String source) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param authUser 提供从第三方获取到的用户信息
     * @return UserDetails 返回注册成功后的用户信息
     * @Name silenceRegister
     * @Description 静默注册方法
     */
    default AuthUserDetails silenceRegister(AuthUser authUser) {
        throw new UnsupportedOperationException();
    }

    /**
     * 判断数据库中用户是否已绑定指定来源的第三方数据
     * @param userId 用户唯一凭证（默认为用户id）
     * @param source 第三方应该来源
     */
    default boolean loadUserBySource(String userId, String source){
        throw new UnsupportedOperationException();
    }

    /** 第三方应用绑定操作
     * @param userId 用户唯一凭证
     * @param providerId 第三方唯一凭证
     * @param source 第三方来源
     */
    default void binding(String userId, String providerId, String source){
        throw new UnsupportedOperationException();
    }
}
