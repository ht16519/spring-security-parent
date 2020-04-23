package com.xh.sso.client01.commons.utils;

import com.xh.sso.client01.commons.enums.MessageEnum;
import com.xh.sso.client01.commons.exception.BusinessException;
import com.xh.sso.client01.domain.auth.UserDetailsVo;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Name UserDetailsVoUtil
 * @Description UserDetailsVo操作工具类
 * @Author wen
 * @Date 2020-04-09
 */
public class UserDetailsVoUtil {

    public static UserDetailsVo get() {
        try {
            UserDetailsVo userDetailsVo = (UserDetailsVo) SecurityContextHolder.getContext().getAuthentication().getCredentials();
            return userDetailsVo;
        } catch (Exception e) {
            throw BusinessException.build(MessageEnum.INVALID_USER_INFO);
        }
    }



}
