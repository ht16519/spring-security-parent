package com.xh.demo.auth.service;

import com.xh.security.core.service.PermissionService;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

/**
 * @Name PermissionServiceImpl
 * @Description
 * @Author wen
 * @Date 2020-04-21
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Override
    public Set<String> loadPermissionByUsername(String username) {
        Set<String> urls = new HashSet<>();
        urls.add("");
        return urls;
    }

}
