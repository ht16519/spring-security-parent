package com.xh.security.core.authentiation.validate.filter;

import com.xh.security.core.authentiation.validate.processor.ValidateCodeProcessor;
import com.xh.security.core.consts.BeanNameConst;
import com.xh.security.core.consts.URLConst;
import com.xh.security.core.exception.ValidateCodeException;
import com.xh.security.core.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Name CodeValidateFilter
 * @Description 验证码校验过滤器
 * @Author wen
 * @Date 2020-04-09
 */
public class ValidateCodeAuthenticationFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    private Map<String, String> urlsMap = new HashMap<>();

    private Map<String, ValidateCodeProcessor> validateCodeProcessor;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ValidateCodeProcessor validateCodeProcessor = validateCodeProcessor(request);
        if (null != validateCodeProcessor) {
            try {
                validateCodeProcessor.validate(request, response);
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private ValidateCodeProcessor validateCodeProcessor(HttpServletRequest request) {
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            for (String url : urlsMap.keySet()) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    return validateCodeProcessor.get(urlsMap.get(url));
                }
            }
        }
        return null;
    }

    public ValidateCodeAuthenticationFilter(AuthenticationFailureHandler authenticationFailureHandler, SecurityProperties securityProperties, Map<String, ValidateCodeProcessor> validateCodeProcessor) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.validateCodeProcessor = validateCodeProcessor;
        initializtionUrlsMap(securityProperties);
    }

    /**
     * 初始化需要校验的路径到Map
     */
    private void initializtionUrlsMap(SecurityProperties securityProperties) {
        //1.获取需要校验图形验证码的路径
        addUrlToMap(securityProperties.getCode().getImage().getUrls(), BeanNameConst.IMAGE_CODE_VALID_PROCESSOR_BEAN_NAME);
        urlsMap.put(URLConst.AUTHENTICATION_FORM_PATH, BeanNameConst.IMAGE_CODE_VALID_PROCESSOR_BEAN_NAME);
        //2.获取需要校验手机验证码的路径
        addUrlToMap(securityProperties.getCode().getSms().getUrls(), BeanNameConst.SMS_CODE_VALID_PROCESSOR_BEAN_NAME);
        urlsMap.put(URLConst.AUTHENTICATION_MOBILE_PATH, BeanNameConst.SMS_CODE_VALID_PROCESSOR_BEAN_NAME);
    }

    /**
     * 将系统中配置的需要校验验证码的URL根据校验的类型放入map
     */
    private void addUrlToMap(String urlString, String type) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlsMap.put(url, type);
            }
        }
    }

}
