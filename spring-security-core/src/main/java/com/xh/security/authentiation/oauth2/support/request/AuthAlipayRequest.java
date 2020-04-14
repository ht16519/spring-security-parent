//package com.xh.security.authentiation.oauth2.support.request;
//
//import com.alipay.api.AlipayApiException;
//import com.alipay.api.AlipayClient;
//import com.alipay.api.DefaultAlipayClient;
//import com.alipay.api.request.AlipaySystemOauthTokenRequest;
//import com.alipay.api.request.AlipayUserInfoShareRequest;
//import com.alipay.api.response.AlipaySystemOauthTokenResponse;
//import com.alipay.api.response.AlipayUserInfoShareResponse;
//import com.xh.security.authentiation.oauth2.support.cache.AuthStateCache;
//import com.xh.security.authentiation.oauth2.support.config.AuthConfig;
//import com.xh.security.authentiation.oauth2.support.config.AuthDefaultSource;
//import com.xh.security.authentiation.oauth2.support.enums.AuthUserGender;
//import com.xh.security.authentiation.oauth2.support.model.AuthCallback;
//import com.xh.security.authentiation.oauth2.support.model.AuthToken;
//import com.xh.security.authentiation.oauth2.support.model.AuthUser;
//import com.xh.security.authentiation.oauth2.support.utils.UrlBuilder;
//
///**
// * 支付宝登录
// */
//public class AuthAlipayRequest extends AuthDefaultRequest {
//
//    private AlipayClient alipayClient;
//
//    public AuthAlipayRequest(AuthConfig config) {
//        super(config, AuthDefaultSource.ALIPAY);
//        this.alipayClient = new DefaultAlipayClient(AuthDefaultSource.ALIPAY.accessToken(), config.getClientId(), config.getClientSecret(), "json", "UTF-8", config
//            .getAlipayPublicKey(), "RSA2");
//    }
//
//    public AuthAlipayRequest(AuthConfig config, AuthStateCache authStateCache) {
//        super(config, AuthDefaultSource.ALIPAY, authStateCache);
//        this.alipayClient = new DefaultAlipayClient(AuthDefaultSource.ALIPAY.accessToken(), config.getClientId(), config.getClientSecret(), "json", "UTF-8", config
//            .getAlipayPublicKey(), "RSA2");
//    }
//
//    @Override
//    protected AuthToken getAccessToken(AuthCallback authCallback) {
//        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
//        request.setGrantType("authorization_code");
//        request.setCode(authCallback.getAuth_code());
//        AlipaySystemOauthTokenResponse response = null;
//        try {
//            response = this.alipayClient.execute(request);
//        } catch (Exception e) {
//            throw new AuthException(e);
//        }
//        if (!response.isSuccess()) {
//            throw new AuthException(response.getSubMsg());
//        }
//        return AuthToken.builder()
//            .accessToken(response.getAccessToken())
//            .uid(response.getUserId())
//            .expireIn(Integer.parseInt(response.getExpiresIn()))
//            .refreshToken(response.getRefreshToken())
//            .build();
//    }
//
//    @Override
//    protected AuthUser getUserInfo(AuthToken authToken) {
//        String accessToken = authToken.getAccessToken();
//        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
//        AlipayUserInfoShareResponse response = null;
//        try {
//            response = this.alipayClient.execute(request, accessToken);
//        } catch (AlipayApiException e) {
//            throw new AuthException(e.getErrMsg(), e);
//        }
//        if (!response.isSuccess()) {
//            throw new AuthException(response.getSubMsg());
//        }
//
//        String province = response.getProvince(), city = response.getCity();
//        String location = String.format("%s %s", StringUtils.isEmpty(province) ? "" : province, StringUtils.isEmpty(city) ? "" : city);
//
//        return AuthUser.builder()
//            .uuid(response.getUserId())
//            .username(StringUtils.isEmpty(response.getUserName()) ? response.getNickName() : response.getUserName())
//            .nickname(response.getNickName())
//            .avatar(response.getAvatar())
//            .location(location)
//            .gender(AuthUserGender.getRealGender(response.getGender()))
//            .token(authToken)
//            .source(source.toString())
//            .build();
//    }
//
//    /**
//     * 返回带{@code state}参数的授权url，授权回调时会带上这个{@code state}
//     *
//     * @param state state 验证授权流程的参数，可以防止csrf
//     * @return 返回授权地址
//     * @since 1.9.3
//     */
//    @Override
//    public String authorize(String state) {
//        return UrlBuilder.fromBaseUrl(source.authorize())
//            .queryParam("app_id", config.getClientId())
//            .queryParam("scope", "auth_user")
//            .queryParam("redirect_uri", config.getRedirectUri())
//            .queryParam("state", getRealState(state))
//            .build();
//    }
//}
