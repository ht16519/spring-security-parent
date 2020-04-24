package com.xh.sso.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Name AuditLogFilter
 * @Description 审计日志过滤器
 * @Author wen
 * @Date 2020-04-24
 */
@Slf4j
@Component
public class AuditLogFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("【网关审计日志服务】audit log insert...");//TODO 记入日志
        return null;
    }
}
