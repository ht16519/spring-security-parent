package com.xh.sso.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xh.sso.gateway.model.TokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name AuthorizationFilter
 * @Description 授权过滤器
 * @Author wen
 * @Date 2020-04-24
 */
@Slf4j
@Component
public class AuthorizationFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    private boolean hasPermission(TokenInfo tokenInfo, HttpServletRequest request) {
        //TODO 判断是否拥有访问权限
        return true;
    }

    private void handleError(int httpStatus, RequestContext currentContext) {
        currentContext.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        currentContext.setResponseStatusCode(httpStatus);
        currentContext.setResponseBody("{\"msg\":\"auth fail\"}");
        currentContext.setSendZuulResponse(false);
    }

    private boolean isNeedAuth() {
        //TODO 判断是否需要认证
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("【网关授权服务】audit log insert...");//TODO 记入日志
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        if (isNeedAuth()) {
            TokenInfo tokenInfo = (TokenInfo) request.getAttribute("tokenInfo");
            if (null != tokenInfo && tokenInfo.isActive()) {
                if (!hasPermission(tokenInfo, request)) {
                    log.info("【网关审计日志服务】audit log update fail 403");
                    handleError(403, currentContext);
                }
                //添加用户信息到请求头
                currentContext.addZuulRequestHeader("username", tokenInfo.getUser_name());
                currentContext.addZuulRequestHeader("userId", String.valueOf(tokenInfo.getId()));
            } else {
                if (!StringUtils.startsWithIgnoreCase(request.getRequestURI(), "/auth")) {
                    log.info("【网关审计日志服务】audit log update fail 401");
                    handleError(401, currentContext);
                }
            }
        }

        return null;
    }
}
