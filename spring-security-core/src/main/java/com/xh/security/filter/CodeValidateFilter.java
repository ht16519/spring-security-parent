package com.xh.security.filter;

import com.xh.security.consts.KeyConst;
import com.xh.security.consts.URLConst;
import com.xh.security.exception.ValidateCodeException;
import com.xh.security.properties.SecurityProperties;
import com.xh.security.validate.code.ImageCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @Name CodeValidateFilter
 * @Description 验证码校验过滤器
 * @Author wen
 * @Date 2020-04-09
 */
public class CodeValidateFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SecurityProperties securityProperties;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    private Set<String> urls = new HashSet<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean action = false;
        for (String url : urls) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                action = true;
            }
        }
        if (action) {
            try {
                validate(request);
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * @Name validate
     * @Description 验证码校验
     */
    private void validate(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String inputImageCode = request.getParameter("imageCode");
        if (StringUtils.isBlank(inputImageCode)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        ImageCode sessionImageCode = (ImageCode) session.getAttribute(KeyConst.IMAGE_CODE_KEY);
        if (null == sessionImageCode || StringUtils.isBlank(sessionImageCode.getCode())) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (sessionImageCode.isExpire()) {
            session.removeAttribute(KeyConst.IMAGE_CODE_KEY);
            throw new ValidateCodeException("验证码已失效");
        }
        if (!StringUtils.equalsIgnoreCase(sessionImageCode.getCode(), inputImageCode)) {
            throw new ValidateCodeException("验证码不正确");
        }
        session.removeAttribute(KeyConst.IMAGE_CODE_KEY);
    }

    public CodeValidateFilter(AuthenticationFailureHandler authenticationFailureHandler, SecurityProperties securityProperties) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.securityProperties = securityProperties;
        String configUrlsStr = securityProperties.getCode().getImage().getUrls();
        if(StringUtils.isNotBlank(configUrlsStr)){
            String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(configUrlsStr, ",");
            for (String url : configUrls) {
                this.urls.add(url);
            }
        }
        this.urls.add(URLConst.AUTHENTICATION_FORM_PATH);
    }

}
