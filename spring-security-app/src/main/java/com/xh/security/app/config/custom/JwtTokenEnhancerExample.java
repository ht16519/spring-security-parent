package com.xh.security.app.config.custom;

import com.xh.security.core.utils.JsonUtil;
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
@Deprecated
public class JwtTokenEnhancerExample implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> info = new HashMap<>();
        info.put("test info", "apend info value");
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        log.debug("OAuth2Authentication:" + JsonUtil.serialize(authentication));
        return accessToken;
    }

}
