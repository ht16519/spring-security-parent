package com.xh.security.core.authorize;

import com.xh.security.core.authorize.service.RbacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

/**
 * @Name RbacWebSecurityExpressionHandler
 * @Description Rbac的权限表达式解析器
 * @Author wen
 * @Date 2020-04-25
 */
@Deprecated
public class RbacWebSecurityExpressionHandler extends OAuth2WebSecurityExpressionHandler {

    @Autowired
    private RbacService rbacService;

    @Override
    protected StandardEvaluationContext createEvaluationContextInternal(Authentication authentication, FilterInvocation invocation) {
        StandardEvaluationContext sec = super.createEvaluationContextInternal(authentication, invocation);
        sec.setVariable("rbacService", rbacService);
        return sec;
    }
}
