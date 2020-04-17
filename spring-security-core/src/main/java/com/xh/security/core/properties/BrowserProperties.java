package com.xh.security.core.properties;

import com.xh.security.core.consts.URLConst;
import com.xh.security.core.enums.LoginEnum;
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
    private String loginPage = URLConst.SIMPLE_SIGNIN_PAGE_PATH;

    /** 默认登出成功定向页面 */
    private String logoutPage = this.loginPage;

    /** 认证结果返回类型 */
    private LoginEnum loginType = LoginEnum.JSON;

    /** 记住我功能的超时时间（默认7天） */
    private int rememberMeSeconds = 60 * 60 * 24 * 7;

    /** 浏览器session相关配置*/
    private BrowserSessionProperties session = new BrowserSessionProperties();

    /** 配置放行的访问路径路径 , 号分隔*/
    private String permitUrls;

}
