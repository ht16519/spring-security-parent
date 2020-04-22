package com.xh.security.app.handler;

import com.xh.security.core.authentiation.oauth2.support.model.AuthResponse;
import com.xh.security.core.consts.CommonConst;
import com.xh.security.core.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

/**
 * @Name XhAuthenticationSuccessHandler
 * @Description 登录认证成功处理类
 * @Author wen
 * @Date 2020-04-09
 */
@Slf4j
public class AppAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    /**
     * @Name onAuthenticationSuccess
     * @Description 该方法登录成功会被调用
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //1.读取请求头中Authorization信息
        String header = request.getHeader("Authorization");
        if (header == null || !header.toLowerCase().startsWith("basic ")) {
            ResponseUtil.write(AuthResponse.failure("Authorization请求头信息不合法"), response);
        }
        //2.获取clientId 和 clientSecret
        String[] tokens = extractAndDecodeHeader(header);
        assert tokens.length == 2;
        String clientId = tokens[0];
        String clientSecret = tokens[1];
        //3.校验clientId 和 clientSecret
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if (clientDetails == null) {
            ResponseUtil.write(AuthResponse.failure("clientId对应信息不存在" + clientId), response);
        } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
            ResponseUtil.write(AuthResponse.failure("clientSecret信息不匹配" + clientId), response);
        }
        //4.构建TokenRequest
        TokenRequest tokenRequest = new TokenRequest(Collections.emptyMap(), clientId, clientDetails.getScope(), "custom");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        //5.创建令牌并返回
        OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        log.info("登录成功...");
        ResponseUtil.write(accessToken, response);
    }

    /**
     * @Name extractAndDecodeHeader
     * @Description 解析请求头信息
     * @Author wen
     * @Date 2020/4/18
     */
    private String[] extractAndDecodeHeader(String header)
            throws IOException {
        byte[] base64Token = header.substring(6).getBytes(CharEncoding.UTF_8);
        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }
        String token = new String(decoded, CharEncoding.UTF_8);
        int delim = token.indexOf(CommonConst.COLON);
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }

}
