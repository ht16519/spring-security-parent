package com.xh.security.core.authentiation.oauth2.support.request;

import com.alibaba.fastjson.JSONObject;
import com.xh.security.core.utils.cache.AuthCache;
import com.xh.security.core.authentiation.oauth2.support.config.AuthConfig;
import com.xh.security.core.authentiation.oauth2.support.config.AuthDefaultSource;
import com.xh.security.core.authentiation.oauth2.support.enums.AuthUserGender;
import com.xh.security.core.authentiation.oauth2.support.exception.AuthException;
import com.xh.security.core.authentiation.oauth2.support.model.AuthCallback;
import com.xh.security.core.authentiation.oauth2.support.model.AuthToken;
import com.xh.security.core.authentiation.oauth2.support.model.AuthUser;
import com.xh.security.core.authentiation.oauth2.support.utils.GlobalAuthUtils;
import com.xh.security.core.authentiation.oauth2.support.utils.UrlBuilder;

/**
 * 淘宝登录
 */
public class AuthTaobaoRequest extends AuthDefaultRequest {

    public AuthTaobaoRequest(AuthConfig config, AuthCache authCache) {
        super(config, AuthDefaultSource.TAOBAO, authCache);
    }

    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        return AuthToken.builder().accessCode(authCallback.getCode()).build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String response = doPostAuthorizationCode(authToken.getAccessCode());
        JSONObject accessTokenObject = JSONObject.parseObject(response);
        if (accessTokenObject.containsKey("error")) {
            throw new AuthException(accessTokenObject.getString("error_description"));
        }
        authToken.setAccessToken(accessTokenObject.getString("access_token"));
        authToken.setRefreshToken(accessTokenObject.getString("refresh_token"));
        authToken.setExpireIn(accessTokenObject.getIntValue("expires_in"));
        authToken.setUid(accessTokenObject.getString("taobao_user_id"));
        authToken.setOpenId(accessTokenObject.getString("taobao_open_uid"));

        String nick = GlobalAuthUtils.urlDecode(accessTokenObject.getString("taobao_user_nick"));
        return AuthUser.builder()
            .uuid(accessTokenObject.getString("taobao_user_id"))
            .username(nick)
            .nickname(nick)
            .gender(AuthUserGender.UNKNOWN)
            .token(authToken)
            .source(source.toString())
            .build();
    }

    /**
     * 返回带{@code state}参数的授权url，授权回调时会带上这个{@code state}
     *
     * @param state state 验证授权流程的参数，可以防止csrf
     * @return 返回授权地址
     * @since 1.9.3
     */
    @Override
    public String authorize(String state) {
        return UrlBuilder.fromBaseUrl(source.authorize())
            .queryParam("response_type", "code")
            .queryParam("client_id", config.getClientId())
            .queryParam("redirect_uri", config.getRedirectUri())
            .queryParam("view", "web")
            .queryParam("state", getRealState(state))
            .build();
    }
}
