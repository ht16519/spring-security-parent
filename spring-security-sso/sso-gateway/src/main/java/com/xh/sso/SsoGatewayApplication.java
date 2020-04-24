package com.xh.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Name SsoGatewayApplication
 * @Description
 * @Author wen
 * @Date 2020-04-23
 */
@SpringBootApplication
@EnableZuulProxy
public class SsoGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoGatewayApplication.class, args);
    }
}
