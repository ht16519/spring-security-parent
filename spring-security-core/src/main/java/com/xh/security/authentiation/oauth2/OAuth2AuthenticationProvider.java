package com.xh.security.authentiation.oauth2;

import com.xh.security.authentiation.oauth2.details.UserDetails4OAuth2Service;
import com.xh.security.authentiation.oauth2.support.model.AuthUser;
import com.xh.security.authentiation.oauth2.support.model.UserDetails4OAuth2;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Name SmsCodeAuthenticationProvider
 * @Description OAuth2.0登录认证提供者
 * @Author wen
 * @Date 2020-04-10
 */
@Setter
public class OAuth2AuthenticationProvider implements AuthenticationProvider {

    private UserDetails4OAuth2Service userDetails4OAuth2Service;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuth2AuthenticationToken oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        AuthUser authUser = (AuthUser) oauth2AuthenticationToken.getPrincipal();
        UserDetails4OAuth2 userDetails;
        try {
            userDetails = userDetails4OAuth2Service.loadUserByProviderId(authUser.getUuid());
        }catch (RuntimeException e){
            throw new UsernameNotFoundException("无法获取用户信息");
        }
        if (null == userDetails || null == userDetails.getUserId()) {
            try {
                userDetails = userDetails4OAuth2Service.silenceRegister(authUser);
            }catch (RuntimeException e){
                throw new UsernameNotFoundException("用户信息保存失败");
            }
        }
        if (null == userDetails || null == userDetails.getUserId()) {
            throw new UsernameNotFoundException("无法获取用户信息");
        }
        OAuth2AuthenticationToken authenticationToken = new OAuth2AuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationToken.setDetails(oauth2AuthenticationToken.getDetails());
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2AuthenticationToken.class.isAssignableFrom(authentication);
    }
}
