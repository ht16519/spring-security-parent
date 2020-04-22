package com.xh.demo.auth;

import com.xh.demo.service.Oauth2UserInfoService;
import com.xh.demo.service.UserService;
import com.xh.security.core.authentiation.oauth2.support.details.SocialUserDetailsService;
import com.xh.security.core.authentiation.oauth2.support.model.AuthUser;
import com.xh.security.core.authentiation.oauth2.support.model.details.AuthUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Name CustomUserDetails4OAuth2Service
 * @Description 自定义OAuth2.0登录服务类
 * @Author wen
 * @Date 2020-04-13
 */
@Slf4j
@Component
public class CustomSocialUserDetailsService implements SocialUserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private Oauth2UserInfoService oauth2UserInfoService;

    @Override
    public AuthUserDetails loadUserByProviderId(String providerId, String source) {
        log.info("【登录认证服务】第三方授权登录操作:{}", providerId);
        return userService.getByProviderId(providerId, source);
    }

    @Override
    public AuthUserDetails silenceRegister(AuthUser authUser) throws UsernameNotFoundException {
        log.info("【用户注册服务】静默注册第三方用户信息:{}", authUser);
        return userService.register(authUser);
    }

    @Override
    public boolean loadUserBySource(Object userId, String source) {
        return oauth2UserInfoService.existBySourceAndUserId(source);
    }
}
