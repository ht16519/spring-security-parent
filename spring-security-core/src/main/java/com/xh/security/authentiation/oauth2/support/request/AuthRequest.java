package com.xh.security.authentiation.oauth2.support.request;

import com.xh.security.authentiation.oauth2.support.cache.AuthStateCache;
import com.xh.security.authentiation.oauth2.support.config.AuthSource;
import com.xh.security.authentiation.oauth2.support.enums.AuthResponseStatus;
import com.xh.security.authentiation.oauth2.support.exception.AuthException;
import com.xh.security.authentiation.oauth2.support.model.AuthCallback;
import com.xh.security.authentiation.oauth2.support.model.AuthResponse;
import com.xh.security.authentiation.oauth2.support.model.AuthToken;
import com.xh.security.authentiation.oauth2.support.model.AuthUser;

/**
 * JustAuth {@code Request}公共接口，所有平台的{@code Request}都需要实现该接口
 */
public interface AuthRequest {

    /**
    * @Name getAuthUser
    * @Description 获取第三方用户信息
    * @Author wen
    * @Date 2020/4/13
    */
    AuthUser getAuthUser(AuthCallback authCallback);

    /**
    * @Name getOpenId
    * @Description 获取第三方应用的openId
    * @Author wen
    * @Date 2020/4/13
    */
    String getOpenId(AuthCallback authCallback);

    /**
     * 返回带{@code state}参数的授权url，授权回调时会带上这个{@code state}
     *
     * @param state state 验证授权流程的参数，可以防止csrf
     * @return 返回授权地址
     */
    default String authorize(String state) {
        throw new AuthException(AuthResponseStatus.NOT_IMPLEMENTED);
    }

    /**
     * 第三方登录
     *
     * @param authCallback 用于接收回调参数的实体
     * @return 返回登录成功后的用户信息
     */
    default AuthResponse login(AuthCallback authCallback) {
        throw new AuthException(AuthResponseStatus.NOT_IMPLEMENTED);
    }

    /**
     * 撤销授权
     *
     * @param authToken 登录成功后返回的Token信息
     * @return AuthResponse
     */
    default AuthResponse revoke(AuthToken authToken) {
        throw new AuthException(AuthResponseStatus.NOT_IMPLEMENTED);
    }

    /**
     * 刷新access token （续期）
     *
     * @param authToken 登录成功后返回的Token信息
     * @return AuthResponse
     */
    default AuthResponse refresh(AuthToken authToken) {
        throw new AuthException(AuthResponseStatus.NOT_IMPLEMENTED);
    }

    AuthSource getSource();

    AuthStateCache getAuthStateCache();

}
