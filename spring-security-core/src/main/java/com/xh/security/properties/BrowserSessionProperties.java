package com.xh.security.properties;

import com.xh.security.consts.URLConst;
import lombok.Getter;
import lombok.Setter;

/**
 * @Name BrowserSessionProperties
 * @Description 浏览器session相关配置
 * @Author wen
 * @Date 2020-04-16
 */
@Getter
@Setter
public class BrowserSessionProperties {

    /** 配置最大并发登录session数 */
    private int maximumSessions = 1;

    /** 当并发session数达到最大，是否阻止后面用户登录 */
    private boolean maxSessionsPreventsLogin;

    /** session失效定重向地址*/
    private String sessionInvalidUrl = URLConst.SIMPLE_SIGNIN_PAGE_PATH;
}
