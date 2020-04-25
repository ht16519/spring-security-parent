package com.xh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
public class SsoClient02Application {

    @GetMapping("/user")
    public Authentication authorization(Authentication user){
        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(SsoClient02Application.class, args);
    }
}
