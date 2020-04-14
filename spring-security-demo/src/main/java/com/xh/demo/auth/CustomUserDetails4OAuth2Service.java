package com.xh.demo.auth;

import com.xh.demo.domain.po.User;
import com.xh.demo.domain.vo.UserDetails4OAuth2Vo;
import com.xh.demo.domain.vo.UserDetailsVo;
import com.xh.demo.service.UserService;
import com.xh.security.authentiation.oauth2.details.UserDetails4OAuth2Service;
import com.xh.security.authentiation.oauth2.support.model.AuthUser;
import com.xh.security.authentiation.oauth2.support.model.UserDetails4OAuth2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Name CustomUserDetails4OAuth2Service
 * @Description 自定义OAuth2.0登录服务类
 * @Author wen
 * @Date 2020-04-13
 */
@Slf4j
@Component
public class CustomUserDetails4OAuth2Service implements UserDetails4OAuth2Service {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails4OAuth2 loadUserByProviderId(String providerId) {
        log.info("【登录认证服务】第三方授权登录操作:{}", providerId);
        return userService.getByProviderId(providerId);
    }

    @Override
    public UserDetails4OAuth2 silenceRegister(AuthUser authUser) throws UsernameNotFoundException {
        log.info("【用户注册服务】静默注册第三方用户信息:{}", authUser);
        return userService.register(authUser);
    }
}
