package com.xh.demo.config.custom;

import com.xh.demo.domain.auth.UserDetailsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Name CustomJwtTokenEnhancer
 * @Description 自定义JWT的token增强器（示例）
 * @Author wen
 * @Date 2020-04-20
 */
@Slf4j
public class CustomJwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        UserDetailsVo userDetailsVo = (UserDetailsVo)authentication.getPrincipal();
        Map<String, Object> info = new HashMap<>();
        info.put("id", userDetailsVo.getId());
        info.put("nickname", userDetailsVo.getNickname());
        info.put("avatar", userDetailsVo.getAvatar());
        info.put("sex", userDetailsVo.getSex());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }

}
