package com.xh.security.core.authentiation.oauth2.config;

import com.xh.security.core.authentiation.oauth2.support.config.AuthConfig;
import com.xh.security.core.authentiation.oauth2.support.request.AuthGiteeRequest;
import com.xh.security.core.authentiation.oauth2.support.request.AuthRequest;
import com.xh.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Name OAuth2Config
 * @Description
 * @Author wen
 * @Date 2020-04-12
 */
@Configuration
@ConditionalOnExpression("'${xh.security.oauth2.siteDomain}'.matches('^(http|https)://.*')")
public class OAuth2Config {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean("gitee")
    @ConditionalOnExpression("!'${xh.security.oauth2.gitee.clientId}'.contains('xh.security.oauth2') ")
    public AuthRequest authGiteeRequest() {
        return new AuthGiteeRequest(AuthConfig.builder()
                .clientId(securityProperties.getOauth2().getGitee().getClientId())
                .clientSecret(securityProperties.getOauth2().getGitee().getClientSecret())
                .redirectUri(securityProperties.getOauth2().getSiteDomain() + "/oauth2/callback")
                .build());
    }

}
