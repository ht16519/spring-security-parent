package com.xh.demo.commons.utils;

import com.xh.demo.commons.enums.MessageEnum;
import com.xh.demo.commons.exception.BusinessException;
import com.xh.demo.domain.vo.UserDetailsVo;
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
