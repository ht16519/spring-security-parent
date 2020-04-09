package com.xh.security.properties;

import com.xh.security.consts.URLConst;
import com.xh.security.enums.LoginEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @Name BrowserProperties
 * @Description PC端安全属性配置类
 * @Author wen
 * @Date 2020-04-09
 */
@Getter
@Setter
public class BrowserProperties {

    /** 默认统一登录认证页面 */
    private String loginPage = "/simple-signIn-page.html";

    /** 认证结果返回类型 */
    private LoginEnum loginType = LoginEnum.JSON;

}
