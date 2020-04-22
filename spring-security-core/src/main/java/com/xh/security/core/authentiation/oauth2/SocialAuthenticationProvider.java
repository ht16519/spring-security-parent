package com.xh.security.core.authentiation.oauth2;

import com.xh.security.core.authentiation.oauth2.support.details.SocialUserDetailsService;
import com.xh.security.core.authentiation.oauth2.support.model.AuthUser;
import com.xh.security.core.authentiation.oauth2.support.model.details.AuthUserDetails;
import com.xh.security.core.exception.AuthenticationBusinessException;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @Name SmsCodeAuthenticationProvider
 * @Description OAuth2.0登录认证提供者
 * @Author wen
 * @Date 2020-04-10
 */
@Setter
public class SocialAuthenticationProvider implements AuthenticationProvider {

    private SocialUserDetailsService userDetails4OAuth2Service;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SocialAuthenticationToken oauth2AuthenticationToken = (SocialAuthenticationToken) authentication;
        AuthUser authUser = (AuthUser) oauth2AuthenticationToken.getPrincipal();
        AuthUserDetails userDetails;
        String source = authUser.getSource();
        if(StringUtils.isEmpty(source)){
            throw new AuthenticationBusinessException("第三方来源不能为空");
        }
        try {
            userDetails = userDetails4OAuth2Service.loadUserByProviderId(authUser.getUuid(), source);
        }catch (RuntimeException e){
            throw new AuthenticationBusinessException("无法获取用户信息");
        }
        if (null == userDetails || null == userDetails.getUserId()) {
            try {
                userDetails = userDetails4OAuth2Service.silenceRegister(authUser);
            }catch (RuntimeException e){
                throw new AuthenticationBusinessException("用户信息保存失败");
            }
        }
        if (null == userDetails || null == userDetails.getUserId()) {
            throw new AuthenticationBusinessException("无法获取用户信息");
        }
        SocialAuthenticationToken authenticationToken = new SocialAuthenticationToken(userDetails, source, userDetails.getAuthorities());
        authenticationToken.setDetails(oauth2AuthenticationToken.getDetails());
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SocialAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
