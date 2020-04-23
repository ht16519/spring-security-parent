package com.xh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Name SsoServerAaalication
 * @Description
 * @Author wen
 * @Date 2020-04-20
 */
@MapperScan(basePackages = {"com.xh.sso.client01.dao.mapper"})
@SpringBootApplication
@EnableOAuth2Sso
public class SsoClient01Application {


    public static void main(String[] args) {
        SpringApplication.run(SsoClient01Application.class, args);
    }
}
