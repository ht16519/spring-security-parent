package com.xh.security.core.authentiation.oauth2;

import com.xh.security.core.authentiation.oauth2.support.model.AuthCallback;
import com.xh.security.core.authentiation.oauth2.support.model.AuthUser;
import com.xh.security.core.authentiation.oauth2.support.request.AuthRequest;
import com.xh.security.core.authentiation.oauth2.support.utils.AuthChecker;
import com.xh.security.core.authentiation.oauth2.support.utils.ConvertUtil;
import com.xh.security.core.consts.URLConst;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetailsSource;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    // ~ Constructors
    // ===================================================================================================

    public SocialAuthenticationFilter() {
        super(new AntPathRequestMatcher(URLConst.AUTHENTICATION_OAUTH2_PATH, "GET"));
    }

    // ~ Methods
    // ========================================================================================================

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("GET")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        AuthRequest authRequest = AuthChecker.checkStateAndGetAuthRequest(authRequestMap, request);

        AuthCallback authCallback = ConvertUtil.toAuthCallback(request);

        AuthChecker.checkCode(authRequest.getSource(), authCallback);

        AuthUser authUser = authRequest.getAuthUser(authCallback);

        SocialAuthenticationToken token = new SocialAuthenticationToken(authUser, authUser.getSource());
        // Allow subclasses to set the "details" property
        setDetails(request, token);

        return this.getAuthenticationManager().authenticate(token);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     * set
     */
    protected void setDetails(HttpServletRequest request,
                              SocialAuthenticationToken authRequest) {
        request.getSession();
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
}
