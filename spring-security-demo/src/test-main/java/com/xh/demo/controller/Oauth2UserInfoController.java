package com.xh.demo.controller;

import java.util.List;
import com.xh.demo.domain.vo.ApiResult;
import lombok.extern.slf4j.Slf4j;
import com.xh.demo.domain.po.Oauth2UserInfo;
import com.xh.demo.service.Oauth2UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("oauth2UserInfo")
public class Oauth2UserInfoController {

    @Autowired
    private Oauth2UserInfoService oauth2UserInfoService;

    /**
    * @Name get
    * @Description 通过id获取数据
    * @param id
    */
    @GetMapping("{id}")
    public ApiResult<Oauth2UserInfo> get(@PathVariable("id") Long id) {
        return ApiResult.success(oauth2UserInfoService.getById(id));
    }

    /**
     * @Name add
     * @Description 保存操作
     * @param oauth2UserInfo
     */
    @PostMapping("save")
    public ApiResult save(@RequestBody Oauth2UserInfo oauth2UserInfo) {
        //添加操作
        return ApiResult.res(oauth2UserInfoService.add(oauth2UserInfo));
    }

    /**
    * @Name update
    * @Description 修改操作
    * @param oauth2UserInfo
    */
    @PostMapping("update")
    public ApiResult update(@RequestBody Oauth2UserInfo oauth2UserInfo) {
        //修改操作
        return ApiResult.res(oauth2UserInfoService.update(oauth2UserInfo));
    }

    /**
    * @Name delete
    * @Description 删除操作
    * @param id
    */
    @GetMapping("delete/{id}")
    public ApiResult<Oauth2UserInfo> delete(@PathVariable("id") Long id) {
        oauth2UserInfoService.deleteById(id);
        return ApiResult.success();
    }
}
