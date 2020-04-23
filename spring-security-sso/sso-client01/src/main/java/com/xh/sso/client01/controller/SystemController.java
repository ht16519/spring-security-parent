package com.xh.sso.client01.controller;

import com.xh.sso.client01.domain.vo.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/sys")
public class SystemController {

    @GetMapping("{id}")
    public ApiResult<String> get(@PathVariable("id") Integer id) {
        return ApiResult.success("欢迎" + id + "号选手登出！！！");
    }

    @GetMapping("/test")
    public String hellow() {
        return "hellow world !!!";
    }




}
