package com.xh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Name SsoServerAaalication
 * @Description
 * @Author wen
 * @Date 2020-04-20
 */
@RestController
@SpringBootApplication
@EnableOAuth2Sso
public class SsoClient01Application {

    @GetMapping("/user")
    public Authentication authorization(Authentication user){
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(SsoClient01Application.class, args);
    }
}
