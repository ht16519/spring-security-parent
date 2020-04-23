package com.xh.security.core.authentiation.oauth2;

import com.xh.security.core.authentiation.oauth2.support.details.SocialUserDetailsService;
import com.xh.security.core.authentiation.oauth2.support.model.AuthCallback;
import com.xh.security.core.authentiation.oauth2.support.model.AuthSimpleResult;
import com.xh.security.core.authentiation.oauth2.support.model.AuthStateRequest;
import com.xh.security.core.authentiation.oauth2.support.model.AuthUser;
import com.xh.security.core.authentiation.oauth2.support.request.AuthRequest;
import com.xh.security.core.authentiation.oauth2.support.utils.AuthChecker;
import com.xh.security.core.authentiation.oauth2.support.utils.ConvertUtil;
import com.xh.security.core.consts.URLConst;
import com.xh.security.core.exception.AuthenticationBusinessException;
import com.xh.security.core.utils.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetailsSource;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Name OAuth2AuthenticationFilter
 * @Description OAuth2.0登录校验过滤器
 * @Author wen
 * @Date 2020-04-10
 */
public class SocialAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private boolean postOnly = true;

    protected AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new OAuth2AuthenticationDetailsSource();

    private Map<String, AuthRequest> authRequestMap;

    private SocialUserDetailsService socialUserDetailsService;

    // ~ Constructors
    // ===================================================================================================

    public SocialAuthenticationFilter() {
        super(new AntPathRequestMatcher(URLConst.AUTHENTICATION_OAUTH2_PATH, "GET"));
    }

    // ~ Methods
    // ========================================================================================================

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        AuthStateRequest authStateRequest = AuthChecker.checkStateAndGetAuthRequest(authRequestMap, request);
        AuthRequest authRequest = authStateRequest.getAuthRequest();
        AuthUser authUser;
        String source;
        //一.get请求为第三方认证携带code授权码的回调请求
        if (request.getMethod().equals("GET")) {
            AuthCallback authCallback = ConvertUtil.toAuthCallback(request);
            AuthChecker.checkCode(authRequest.getSource(), authCallback);
            authUser = authRequest.getAuthUser(authCallback);
            source = authUser.getSource();
            //第三方应用绑定操作
            String userId = authStateRequest.getUserId();
            if (StringUtils.isNotEmpty(userId)) {
                socialUserDetailsService.binding(userId, authUser.getUuid(), source);
                ResponseUtil.write(AuthSimpleResult.build("第三方应用绑定成功"), response);
            }
        } else {
            //二.post请求为内部app携带providerId和source的授权请求
            String providerId = request.getParameter("providerId");
            source = request.getParameter("source");
            if (StringUtils.isEmpty(providerId) || StringUtils.isEmpty(source)) {
                throw new AuthenticationBusinessException("参数不合法");
            }
            authUser = authRequest.getAuthUserByProviderId(providerId);
            source = source.toUpperCase();
        }
        SocialAuthenticationToken token = new SocialAuthenticationToken(authUser, source);
        // Allow subclasses to set the "details" property
        setDetails(request, token);
        return this.getAuthenticationManager().authenticate(token);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request     that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     *                    set
     */
    protected void setDetails(HttpServletRequest request,
                              SocialAuthenticationToken authRequest) {
        HttpSession session = request.getSession(false);
        if(null != session){
            session.invalidate();
        }
        request.getSession(true);
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Defines whether only HTTP POST requests will be allowed by this filter. If set to
     * true, and an authentication request is received which is not a POST request, an
     * exception will be raised immediately and authentication will not be attempted. The
     * <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
     * authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public void setAuthRequestMap(Map<String, AuthRequest> authRequestMap) {
        this.authRequestMap = authRequestMap;
    }

    public void setSocialUserDetailsService(SocialUserDetailsService socialUserDetailsService) {
        this.socialUserDetailsService = socialUserDetailsService;
    }
}
