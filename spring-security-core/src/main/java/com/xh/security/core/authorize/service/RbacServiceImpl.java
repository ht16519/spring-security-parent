package com.xh.security.core.authorize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @Name RbacService
 * @Description 加载RBAC权限模型实现
 * @Author wen
 * @Date 2020-04-21
 */
@Service("rbacService")
public class RbacServiceImpl implements RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired(required = false)
    private PermissionService permissionService;

    /**
     * 判断是否有用访问权限
     * 实现逻辑参考：
     * 1.可以系统启动时将所有 角色 和 权限 对应关系加载到缓存中，如redis，
     * 2.通过登录后用户的 角色 查找缓存中的 权限 资源urls 信息，
     * 3.通过 资源urls 判断用户是否拥有访问接口的权限
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        //没有传递token，为匿名用户
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new AccessTokenRequiredException(null);
        }
        //判断用户已登录
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String requestURI = request.getRequestURI();
            UserDetails userDetails = (UserDetails) principal;
            //1.加载用户拥有的访问资源路径集合
            String username = userDetails.getUsername();
            Set<String> urls = permissionService.loadResourcesUrls(username, getRoles(userDetails.getAuthorities()));
            //2.判断用户是否拥有访问的权限
            for (String url : urls) {
                if (antPathMatcher.match(url, requestURI)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取用户的角色
     */
    private Set<String> getRoles(Collection<? extends GrantedAuthority> authorities) {
        Set<String> roleSet = new HashSet<>();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        while (iterator.hasNext()) {
            GrantedAuthority next = iterator.next();
            roleSet.add(next.getAuthority());
        }
        return roleSet;
    }

}
