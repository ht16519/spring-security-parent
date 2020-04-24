package com.xh.sso.server.config.custom;

import com.xh.sso.server.domain.vo.UserVo;
import com.xh.sso.server.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Name CustomJwtTokenEnhancer
 * @Description 自定义token增强器（示例）
 * @Author wen
 * @Date 2020-04-20
 */
@Slf4j
@Component
public class TokenEnhancerExample implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserVo) {
            UserVo userVo = (UserVo) principal;
            Map<String, Object> info = new HashMap<>();
            info.put("id", userVo.getId());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        }
        return accessToken;
    }

}
