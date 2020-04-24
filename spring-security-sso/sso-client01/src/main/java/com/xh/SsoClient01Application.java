package com.xh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Name SsoServerAaalication
 * @Description
 * @Author wen
 * @Date 2020-04-20
 */
@MapperScan(basePackages = {"com.xh.sso.client01.dao.mapper"})
@SpringBootApplication
public class SsoClient01Application {

    public static void main(String[] args) {
        SpringApplication.run(SsoClient01Application.class, args);
    }
}
