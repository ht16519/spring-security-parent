package com.xh.sso.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xh.sso.gateway.model.TokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;

/**
 * @Name OAuth2Filter
 * @Description 认证过滤器
 * @Author wen
 * @Date 2020-04-24
 */
@Slf4j
@Component
public class OAuth2Filter extends ZuulFilter {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public String filterType() {
        //可选值: "pre" "post" "error" "route"
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("【网关认证服务】OAuth Authentication Start...");
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        //Authorization请求头为空放行
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(authorization) && StringUtils.startsWithIgnoreCase(authorization, "bearer ")) {
            try {
                TokenInfo tokenInfo = getTokenInfo(authorization);
                request.setAttribute("tokenInfo", tokenInfo);
            } catch (Exception e) {
                log.info("【网关认证服务】get token info fail:{}", e);
            }
            return null;
        }
        return null;
    }

//    @Override
//    public Object run() throws ZuulException {
//        log.info("【网关认证服务】OAuth Authentication Start...");
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        //发往认证服务器的请求放行
//        if(StringUtils.startsWith(request.getRequestURI(), "/auth")){
//            return null;
//        }
//        //Authorization请求头为空放行
//        String authorization = request.getHeader("Authorization");
//        if(StringUtils.isEmpty(authorization)){
//            return null;
//        }
//        //Authorization请求头包不含bearer放行
//        if(!StringUtils.startsWithIgnoreCase(authorization, "bearer")){
//            return null;
//        }
//        try {
//            TokenInfo tokenInfo = getTokenInfo(authorization);
//            request.setAttribute("tokenInfo", tokenInfo);
//        }catch (Exception e){
//            log.info("【网关认证服务】get token info fail:{}", e.getMessage());
//        }
//        return null;
//    }

    private TokenInfo getTokenInfo(String authorization) {
        String token = StringUtils.substringAfter(authorization, "bearer ");
        String oauthServiceUrl = "http://localhost:9999/auth/oauth/check_token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", getHeader("gateway", "ht16519"));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        ResponseEntity<String> exchange = restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, entity, String.class);
        String body = exchange.getBody();
        ResponseEntity<TokenInfo> responseEntity = restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, entity, TokenInfo.class);
        log.debug("【网关认证服务】token info:{}", responseEntity.getBody());
        return responseEntity.getBody();
    }

    private String getHeader(String clientId, String secretId) {
        String auth = clientId + ":" + secretId;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
        return "Basic " + new String(encodedAuth);
    }
}
