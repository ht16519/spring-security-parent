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
import com.xh.security.core.authentiation.oauth2.support.utils.UrlBuilder;

/**
 * Cooding登录
 */
public class AuthCodingRequest extends AuthDefaultRequest {

    public AuthCodingRequest(AuthConfig config, AuthCache authCache) {
        super(config, AuthDefaultSource.CODING, authCache);
    }

    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        String response = doGetAuthorizationCode(authCallback.getCode());
        JSONObject accessTokenObject = JSONObject.parseObject(response);
        this.checkResponse(accessTokenObject);
        return AuthToken.builder()
            .accessToken(accessTokenObject.getString("access_token"))
            .expireIn(accessTokenObject.getIntValue("expires_in"))
            .refreshToken(accessTokenObject.getString("refresh_token"))
            .build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String response = doGetUserInfo(authToken);
        JSONObject object = JSONObject.parseObject(response);
        this.checkResponse(object);

        object = object.getJSONObject("data");
        return AuthUser.builder()
            .uuid(object.getString("id"))
            .username(object.getString("name"))
            .avatar("https://coding.net/" + object.getString("avatar"))
            .blog("https://coding.net/" + object.getString("path"))
            .nickname(object.getString("name"))
            .company(object.getString("company"))
            .location(object.getString("location"))
            .gender(AuthUserGender.getRealGender(object.getString("sex")))
            .email(object.getString("email"))
            .remark(object.getString("slogan"))
            .token(authToken)
            .source(source.toString())
            .build();
    }

    /**
     * 检查响应内容是否正确
     *
     * @param object 请求响应内容
     */
    private void checkResponse(JSONObject object) {
        if (object.getIntValue("code") != 0) {
            throw new AuthException(object.getString("msg"));
        }
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
            .queryParam("scope", "user")
            .queryParam("state", getRealState(state))
            .build();
    }
}
