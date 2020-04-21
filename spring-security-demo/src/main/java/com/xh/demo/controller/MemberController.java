package com.xh.demo.controller;

import com.xh.demo.domain.vo.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {

    @GetMapping("{id}")
    public ApiResult<String> get(@PathVariable("id") Integer id) {
        return ApiResult.success("欢迎尊贵的VIP：" + id + "用户！！！");
    }

    @GetMapping("/test")
    public String hellow() {
        return "hellow world member !!!";
    }




}
