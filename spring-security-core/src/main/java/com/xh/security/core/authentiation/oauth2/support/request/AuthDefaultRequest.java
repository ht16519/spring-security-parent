package com.xh.security.core.authentiation.oauth2.support.request;


import com.xh.security.core.utils.cache.AuthCache;
import com.xh.security.core.authentiation.oauth2.support.config.AuthConfig;
import com.xh.security.core.authentiation.oauth2.support.config.AuthSource;
import com.xh.security.core.authentiation.oauth2.support.enums.AuthResponseStatus;
import com.xh.security.core.authentiation.oauth2.support.exception.AuthException;
import com.xh.security.core.authentiation.oauth2.support.model.AuthCallback;
import com.xh.security.core.authentiation.oauth2.support.model.AuthResponse;
import com.xh.security.core.authentiation.oauth2.support.model.AuthToken;
import com.xh.security.core.authentiation.oauth2.support.model.AuthUser;
import com.xh.security.core.authentiation.oauth2.support.utils.AuthChecker;
import com.xh.security.core.authentiation.oauth2.support.utils.UrlBuilder;
import com.xh.security.core.authentiation.oauth2.support.utils.UuidUtils;
import com.xkcoding.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 默认的request处理类
 */
@Slf4j
public abstract class AuthDefaultRequest implements AuthRequest {

    private long stateInExpire = 1000 * 60 * 10;    //state失效时间（10 min）

    protected AuthConfig config;
    protected AuthSource source;
    protected AuthCache authCache;

    public AuthDefaultRequest(AuthConfig config, AuthSource source, AuthCache authCache) {
        this.config = config;
        this.source = source;
        this.authCache = authCache;
        if (!AuthChecker.isSupportedAuth(config, source)) {
            throw new AuthException(AuthResponseStatus.PARAMETER_INCOMPLETE, source);
        }
        // 校验配置合法性
        AuthChecker.checkConfig(config, source);
    }

    /**
     * 获取access token
     *
     * @param authCallback 授权成功后的回调参数
     * @return token
     * @see AuthDefaultRequest#
     * @see AuthDefaultRequest#authorize(String)
     */
    protected abstract AuthToken getAccessToken(AuthCallback authCallback);

    /**
     * 使用token换取用户信息
     *
     * @param authToken token信息
     * @return 用户信息
     * @see AuthDefaultRequest#getAccessToken(AuthCallback)
     */
    protected abstract AuthUser getUserInfo(AuthToken authToken);

    /**
     * 统一的登录入口。当通过{@link AuthDefaultRequest#authorize(String)}授权成功后，会跳转到调用方的相关回调方法中
     * 方法的入参可以使用{@code AuthCallback}，{@code AuthCallback}类中封装好了OAuth2授权回调所需要的参数
     *
     * @param authCallback 用于接收回调参数的实体
     * @return AuthResponse
     */
    @Override
    public AuthResponse login(AuthCallback authCallback) {
        try {
            AuthChecker.checkCode(source, authCallback);
            AuthChecker.checkState(authCallback.getState(), source, authCache);
            AuthToken authToken = this.getAccessToken(authCallback);
            AuthUser user = this.getUserInfo(authToken);
            return AuthResponse.builder().code(AuthResponseStatus.SUCCESS.getCode()).data(user).build();
        } catch (Exception e) {
            log.error("Failed to login with oauth authorization.", e);
            return this.responseError(e);
        }
    }

    @Override
    public AuthUser getAuthUser(AuthCallback authCallback) {
        AuthToken authToken = this.getAccessToken(authCallback);
        return this.getUserInfo(authToken);
    }

    @Override
    public String getOpenId(AuthCallback authCallback) {
        return this.getAccessToken(authCallback).getOpenId();
    }

    /**
     * 处理{@link AuthDefaultRequest#login(AuthCallback)} 发生异常的情况，统一响应参数
     *
     * @param e 具体的异常
     * @return AuthResponse
     */
    private AuthResponse responseError(Exception e) {
        int errorCode = AuthResponseStatus.FAILURE.getCode();
        String errorMsg = e.getMessage();
        if (e instanceof AuthException) {
            AuthException authException = ((AuthException) e);
            errorCode = authException.getErrorCode();
            if (StringUtils.isNotEmpty(authException.getErrorMsg())) {
                errorMsg = authException.getErrorMsg();
            }
        }
        return AuthResponse.builder().code(errorCode).msg(errorMsg).build();
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
            .queryParam("state", getRealState(state))
            .build();
    }

    /**
     * 返回获取accessToken的url
     *
     * @param code 授权码
     * @return 返回获取accessToken的url
     */
    protected String accessTokenUrl(String code) {
        return UrlBuilder.fromBaseUrl(source.accessToken())
            .queryParam("code", code)
            .queryParam("client_id", config.getClientId())
            .queryParam("client_secret", config.getClientSecret())
            .queryParam("grant_type", "authorization_code")
            .queryParam("redirect_uri", config.getRedirectUri())
            .build();
    }

    /**
     * 返回获取accessToken的url
     *
     * @param refreshToken refreshToken
     * @return 返回获取accessToken的url
     */
    protected String refreshTokenUrl(String refreshToken) {
        return UrlBuilder.fromBaseUrl(source.refresh())
            .queryParam("client_id", config.getClientId())
            .queryParam("client_secret", config.getClientSecret())
            .queryParam("refresh_token", refreshToken)
            .queryParam("grant_type", "refresh_token")
            .queryParam("redirect_uri", config.getRedirectUri())
            .build();
    }

    /**
     * 返回获取userInfo的url
     *
     * @param authToken token
     * @return 返回获取userInfo的url
     */
    protected String userInfoUrl(AuthToken authToken) {
        return UrlBuilder.fromBaseUrl(source.userInfo()).queryParam("access_token", authToken.getAccessToken()).build();
    }

    /**
     * 返回获取revoke authorization的url
     *
     * @param authToken token
     * @return 返回获取revoke authorization的url
     */
    protected String revokeUrl(AuthToken authToken) {
        return UrlBuilder.fromBaseUrl(source.revoke()).queryParam("access_token", authToken.getAccessToken()).build();
    }

    /**
     * 获取state，如果为空， 则默认取当前日期的时间戳
     *
     * @param state 原始的state
     * @return 返回不为null的state
     */
    protected String getRealState(String state) {
        if (StringUtils.isEmpty(state)) {
            state = UuidUtils.getUUID();
        }
        // 缓存state
        authCache.set(state, "", stateInExpire);
        return state;
    }

    /**
     * 通用的 authorizationCode 协议
     *
     * @param code code码
     * @return Response
     */
    protected String doPostAuthorizationCode(String code) {
        return HttpUtil.post(accessTokenUrl(code));
    }

    /**
     * 通用的 authorizationCode 协议
     *
     * @param code code码
     * @return Response
     */
    protected String doGetAuthorizationCode(String code) {
        return HttpUtil.get(accessTokenUrl(code));
    }

    /**
     * 通用的 用户信息
     *
     * @param authToken token封装
     * @return Response
     */
    @Deprecated
    protected String doPostUserInfo(AuthToken authToken) {
        return HttpUtil.post(userInfoUrl(authToken));
    }

    /**
     * 通用的 用户信息
     *
     * @param authToken token封装
     * @return Response
     */
    protected String doGetUserInfo(AuthToken authToken) {
        return HttpUtil.get(userInfoUrl(authToken));
    }

    /**
     * 通用的post形式的取消授权方法
     *
     * @param authToken token封装
     * @return Response
     */
    @Deprecated
    protected String doPostRevoke(AuthToken authToken) {
        return HttpUtil.post(revokeUrl(authToken));
    }

    /**
     * 通用的get形式的取消授权方法
     *
     * @param authToken token封装
     * @return Response
     */
    protected String doGetRevoke(AuthToken authToken) {
        return HttpUtil.get(revokeUrl(authToken));
    }

    public AuthSource getSource() {
        return source;
    }

    public AuthCache getAuthStateCache() {
        return authCache;
    }

}
