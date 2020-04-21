package com.xh.security.core.controller;

import com.xh.security.core.authentiation.oauth2.support.request.AuthRequest;
import com.xh.security.core.authentiation.oauth2.support.utils.AuthStateUtils;
import com.xh.security.core.enums.LoginEnum;
import com.xh.security.core.exception.AuthenticationBusinessException;
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

    /** OAuth2.0 第三方登录授权地址
     * param source 第三方服务源（如：gitee qq weixin）
     */
    @GetMapping("/{source}/authorize")
    public void authorizeUrl(@PathVariable("source") String source, HttpServletResponse response) throws IOException {
        this.getAuthorizeUrl(source, response, AuthStateUtils.createState(source));
    }

    /** OAuth2.0 已有帐号绑定授权地址
     *  param source 第三方服务源（如：gitee qq weixin）
     *  param id 需要绑定的用户唯一凭证
     */
    @GetMapping("/{source}/binding/{id}")
    public void authorizeUrl(@PathVariable("source") String source, @PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        this.getAuthorizeUrl(source, response, AuthStateUtils.createState(source, id));
    }


    private void getAuthorizeUrl(String source, HttpServletResponse response, String state) throws IOException {
        AuthRequest authRequest = this.authRequestMap.get(source);
        if(null == authRequest){
            throw new AuthenticationBusinessException("无效的授权请求");
        }
        String authorizeUrl = authRequest.authorize(state);
        if (LoginEnum.REDIRECT.equals(securityProperties.getOauth2().getLoginType())) {
            //重定向到授权页面
            response.sendRedirect(authorizeUrl);
        }
        ResponseUtil.write(authorizeUrl, response);
    }


}
