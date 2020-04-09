package com.xh.security.properties;

import com.xh.security.consts.URLConst;
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

    private String loginPage = "/simple-signIn-page.html";
}
