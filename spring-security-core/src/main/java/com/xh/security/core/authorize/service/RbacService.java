package com.xh.security.core.authorize.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name RbacService
 * @Description 加载RBAC权限模型的接口
 * @Author wen
 * @Date 2020-04-25
 */
public interface RbacService {

    boolean hasPermission(HttpServletRequest request, Authentication authentication);

}
