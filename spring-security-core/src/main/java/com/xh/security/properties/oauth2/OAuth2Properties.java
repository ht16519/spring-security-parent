package com.xh.security.properties.oauth2;

import com.xh.security.enums.LoginEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @Name OAuth2Properties
 * @Description
 * @Author wen
 * @Date 2020-04-12
 */
@Getter
@Setter
public class OAuth2Properties {

    /** 定义OAuth2授权链接（定向 | 返回jjson报文信息）*/
    private LoginEnum loginType = LoginEnum.REDIRECT;

    /** 网站域名*/
    private String siteDomain;

    /** 登录成功的重定向页面*/
    private String callBackUrl;

    /** gitee登录认证相关配置*/
    private GiteeProperties gitee = new GiteeProperties();

}
