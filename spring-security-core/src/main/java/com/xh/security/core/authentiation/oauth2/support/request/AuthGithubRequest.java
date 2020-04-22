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

import java.util.Map;

/**
 * Github登录
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @since 1.0.0
 */
public class AuthGithubRequest extends AuthDefaultRequest {

    public AuthGithubRequest(AuthConfig config, AuthCache authCache) {
        super(config, AuthDefaultSource.GITHUB, authCache);
    }

    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        String response = doPostAuthorizationCode(authCallback.getCode());
        Map<String, String> res = GlobalAuthUtils.parseStringToMap(response);

        this.checkResponse(res.containsKey("error"), res.get("error_description"));

        return AuthToken.builder()
            .accessToken(res.get("access_token"))
            .scope(res.get("scope"))
            .tokenType(res.get("token_type"))
            .build();
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        String response = doGetUserInfo(authToken);
        JSONObject object = JSONObject.parseObject(response);

        this.checkResponse(object.containsKey("error"), object.getString("error_description"));

        return AuthUser.builder()
            .uuid(object.getString("id"))
            .username(object.getString("login"))
            .avatar(object.getString("avatar_url"))
            .blog(object.getString("blog"))
            .nickname(object.getString("name"))
            .company(object.getString("company"))
            .location(object.getString("location"))
            .email(object.getString("email"))
            .remark(object.getString("bio"))
            .gender(AuthUserGender.UNKNOWN)
            .token(authToken)
            .source(source.toString())
            .build();
    }

    private void checkResponse(boolean error, String error_description) {
        if (error) {
            throw new AuthException(error_description);
        }
    }

}
