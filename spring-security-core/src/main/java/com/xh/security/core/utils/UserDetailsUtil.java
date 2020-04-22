package com.xh.security.core.utils;

import com.xh.security.core.authentiation.oauth2.support.model.details.AuthUserDetails;
import com.xh.security.core.exception.AuthenticationBusinessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Name UserDetailsUtil
 * @Description UserDetails工具类
 * @Author wen
 * @Date 2020-04-22
 */
public class UserDetailsUtil {

    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        throw new AuthenticationBusinessException("请登录后再尝试此操作");
    }

    public static AuthUserDetails getUserDetailsVo(Authentication authentication) {
        if (null == authentication) {
            throw new AuthenticationBusinessException("请登录后再尝试此操作");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof AuthUserDetails) {
            return ((AuthUserDetails) principal);
        }
        throw new AuthenticationBusinessException("请登录后再尝试此操作");
    }

    public static String getUserId(Authentication authentication) {
        return getUserDetailsVo(authentication).getUserId().toString();
    }

}
