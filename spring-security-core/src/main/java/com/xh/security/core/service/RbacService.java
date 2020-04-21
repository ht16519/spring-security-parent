package com.xh.security.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @Name RbacService
 * @Description RBAC权限模型的接口
 * @Author wen
 * @Date 2020-04-21
 */
@Service("rbacService")
public class RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired(required = false)
    private PermissionService permissionService;

    /**
     * 判断是否有用访问权限
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        //判断用户是否登录
        if (principal instanceof UserDetails) {
            String requestURI = request.getRequestURI();
            UserDetails userDetails = (UserDetails) principal;
            //判断用户是否拥有访问的权限
            String username = userDetails.getUsername();
            Set<String> urls = permissionService.loadPermissionByUsername(username);
            for (String url : urls) {
                if (antPathMatcher.match(url, requestURI)) {
                    return true;
                }
            }
        }
        return false;
    }

}
