package com.xh.security.core.service;

import java.util.Set;

/**
 * @Name PermissionService
 * @Description 权限服务类
 * @Author wen
 * @Date 2020-04-21
 */
public interface PermissionService {

    /**
    * @Name loadPermissionByUsername
    * @Description 通过用户名获取用户权限集合
    * @Author wen
    * @Date 2020/4/21
    */
    Set<String> loadPermissionByUsername(String username);

}
