package com.xh.security.authentiation.mobile;

import com.xh.security.authentiation.mobile.details.UserDetails4MobileService;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Name SmsCodeAuthenticationProvider
 * @Description 手机短信验证码认证提供者
 * @Author wen
 * @Date 2020-04-10
 */
@Setter
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetails4MobileService userDetails4MobileService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken smsCodeAuthenticationToken =  (SmsCodeAuthenticationToken) authentication;
        UserDetails userDetails = userDetails4MobileService.loadUserByMobile((String) smsCodeAuthenticationToken.getPrincipal());
        if(null == userDetails){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        SmsCodeAuthenticationToken authenticationToken = new SmsCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationToken.setDetails(smsCodeAuthenticationToken.getDetails());
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
