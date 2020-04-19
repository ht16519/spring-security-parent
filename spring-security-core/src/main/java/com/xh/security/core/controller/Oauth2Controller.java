package com.xh.security.core.controller;

import com.xh.security.core.authentiation.oauth2.support.request.AuthRequest;
import com.xh.security.core.authentiation.oauth2.support.utils.AuthStateUtils;
import com.xh.security.core.enums.LoginEnum;
import com.xh.security.core.properties.SecurityProperties;
import com.xh.security.core.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Name Oauth2Controller
 * @Description
 * @Author wen
 * @Date 2020-04-12
 */
@Slf4j
@Controller
@RequestMapping("/oauth2")
@ConditionalOnBean(AuthRequest.class)
public class Oauth2Controller {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired(required = false)
    private Map<String, AuthRequest> authRequestMap;

    /** OAuth2.0 授权地址*/
    @GetMapping("/{type}/authorize")
    public void authorizeUrl(@PathVariable("type") String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = this.authRequestMap.get(type);
        if(null == authRequest){
            throw new IllegalArgumentException("无效的授权请求");
        }
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState(type));
        if (LoginEnum.REDIRECT.equals(securityProperties.getOauth2().getLoginType())) {
            //重定向到授权页面
            response.sendRedirect(authorizeUrl);
        }
        ResponseUtil.write(authorizeUrl, response);
    }

}
