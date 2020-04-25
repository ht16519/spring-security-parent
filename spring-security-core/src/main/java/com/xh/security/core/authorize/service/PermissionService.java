package com.xh.security.core.authorize.service;

import java.util.Set;

/**
 * @Name PermissionService
 * @Description 加载权限服务类
 * @Author wen
 * @Date 2020-04-21
 */
public interface PermissionService {

    /**
    * @Name loadPermissions
    * @Description 通过用户名或者用户角色加载用户允许访问的资源路径集合
    * @Author wen
    * @Date 2020/4/21
    */
    Set<String> loadResourcesUrls(String username, Set<String> roles);
}
