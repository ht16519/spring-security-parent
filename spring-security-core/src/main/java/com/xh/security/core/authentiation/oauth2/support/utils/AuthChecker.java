package com.xh.security.core.authentiation.oauth2.support.utils;

import com.xh.security.core.authentiation.oauth2.support.cache.AuthStateCache;
import com.xh.security.core.authentiation.oauth2.support.config.AuthConfig;
import com.xh.security.core.authentiation.oauth2.support.config.AuthDefaultSource;
import com.xh.security.core.authentiation.oauth2.support.config.AuthSource;
import com.xh.security.core.authentiation.oauth2.support.enums.AuthResponseStatus;
import com.xh.security.core.authentiation.oauth2.support.exception.AuthException;
import com.xh.security.core.authentiation.oauth2.support.model.AuthCallback;
import com.xh.security.core.authentiation.oauth2.support.request.AuthRequest;
import com.xh.security.core.consts.CommonConst;
import com.xh.security.core.exception.AuthenticationBusinessException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 授权配置类的校验器
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @since 1.6.1-beta
 */
public class AuthChecker {

    /**
     * 是否支持第三方登录
     *
     * @param config config
     * @param source source
     * @return true or false
     * @since 1.6.1-beta
     */
    public static boolean isSupportedAuth(AuthConfig config, AuthSource source) {
        boolean isSupported = StringUtils.isNotEmpty(config.getClientId()) && StringUtils.isNotEmpty(config.getClientSecret()) && StringUtils.isNotEmpty(config.getRedirectUri());
        if (isSupported && AuthDefaultSource.ALIPAY == source) {
            isSupported = StringUtils.isNotEmpty(config.getAlipayPublicKey());
        }
        if (isSupported && AuthDefaultSource.STACK_OVERFLOW == source) {
            isSupported = StringUtils.isNotEmpty(config.getStackOverflowKey());
        }
        if (isSupported && AuthDefaultSource.WECHAT_ENTERPRISE == source) {
            isSupported = StringUtils.isNotEmpty(config.getAgentId());
        }
        return isSupported;
    }

    /**
     * 检查配置合法性。针对部分平台， 对redirect uri有特定要求。一般来说redirect uri都是http://，而对于facebook平台， redirect uri 必须是https的链接
     *
     * @param config config
     * @param source source
     * @since 1.6.1-beta
     */
    public static void checkConfig(AuthConfig config, AuthSource source) {
        String redirectUri = config.getRedirectUri();
        if (!GlobalAuthUtils.isHttpProtocol(redirectUri) && !GlobalAuthUtils.isHttpsProtocol(redirectUri)) {
            throw new AuthException(AuthResponseStatus.ILLEGAL_REDIRECT_URI, source);
        }
        // facebook的回调地址必须为https的链接
        if (AuthDefaultSource.FACEBOOK == source && !GlobalAuthUtils.isHttpsProtocol(redirectUri)) {
            // Facebook's redirect uri must use the HTTPS protocol
            throw new AuthException(AuthResponseStatus.ILLEGAL_REDIRECT_URI, source);
        }
        // 支付宝在创建回调地址时，不允许使用localhost或者127.0.0.1
        if (AuthDefaultSource.ALIPAY == source && GlobalAuthUtils.isLocalHost(redirectUri)) {
            // The redirect uri of alipay is forbidden to use localhost or 127.0.0.1
            throw new AuthException(AuthResponseStatus.ILLEGAL_REDIRECT_URI, source);
        }
    }

    /**
     * 校验回调传回的code
     * <p>
     * {@code v1.10.0}版本中改为传入{@code source}和{@code callback}，对于不同平台使用不同参数接受code的情况统一做处理
     *
     * @param source   当前授权平台
     * @param callback 从第三方授权回调回来时传入的参数集合
     * @since 1.8.0
     */
    public static void checkCode(AuthSource source, AuthCallback callback) {
        String code = callback.getCode();
        if (source == AuthDefaultSource.ALIPAY) {
            code = callback.getAuth_code();
        } else if (source == AuthDefaultSource.HUAWEI) {
            code = callback.getAuthorization_code();
        }
        if (StringUtils.isEmpty(code)) {
            throw new AuthenticationBusinessException("授权码不存在");
        }
    }

    public static void checkCode(AuthSource source, HttpServletRequest request) {
        String code = request.getParameter("code");
        if (source == AuthDefaultSource.ALIPAY) {
            code = request.getParameter("auth_code");
        } else if (source == AuthDefaultSource.HUAWEI) {
            code = request.getParameter("authorization_code");
        }
        if (StringUtils.isBlank(code)) {
            throw new AuthException("授权码不存在");
        }
    }

    /**
     * 校验回调传回的{@code state}，为空或者不存在
     * <p>
     * {@code state}不存在的情况只有两种：
     * 1. {@code state}已使用，被正常清除
     * 2. {@code state}为前端伪造，本身就不存在
     *
     * @param state          {@code state}一定不为空
     * @param source         {@code source}当前授权平台
     * @param authStateCache {@code authStateCache} state缓存实现
     */
    public static void checkState(String state, AuthSource source, AuthStateCache authStateCache) {
        if (StringUtils.isEmpty(state) || !authStateCache.containsKey(state)) {
            throw new AuthException(AuthResponseStatus.ILLEGAL_STATUS, source);
        }
    }

    /**
     * @Name checkStateAndGetAuthRequest
     * @Description 检查State状态码并且返回AuthRequest
     * @Author wen
     * @Date 2020/4/13
     */
    public static AuthRequest checkStateAndGetAuthRequest(Map<String, AuthRequest> authRequestMap, HttpServletRequest request) {
        String state = request.getParameter("state");
        String[] split;
        if (null == state || (split = state.split(CommonConst.COLON)).length != 2) {
            throw new AuthenticationBusinessException("非法请求");
        }
        AuthRequest authRequest = authRequestMap.get(split[0]);
//        AuthStateCache authStateCache = authRequest.getAuthStateCache();
//        if (!authStateCache.containsKey(state)) {
//            throw new AuthenticationBusinessException("非法请求");
//        }
//        authStateCache.remove(state);
        return authRequest;
    }

}
