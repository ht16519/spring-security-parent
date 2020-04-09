package com.xh.demo.controller;

import com.xh.demo.domain.vo.ApiResult;
import lombok.extern.slf4j.Slf4j;
import com.xh.demo.domain.po.User;
import com.xh.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
    * @Name get
    * @Description 通过id获取数据
    * @param id
    */
    @GetMapping("{id}")
    public ApiResult<User> get(@PathVariable("id") Integer id) {
        return ApiResult.success(userService.getById(id));
    }

    /**
     * @Name add
     * @Description 保存操作
     * @param user
     */
    @PostMapping("save")
    public ApiResult save(@RequestBody User user) {
        //添加操作
        return ApiResult.res(userService.add(user));
    }

    /**
    * @Name update
    * @Description 修改操作
    * @param user
    */
    @PostMapping("update")
    public ApiResult update(@RequestBody User user) {
        //修改操作
        return ApiResult.res(userService.update(user));
    }

    /**
    * @Name delete
    * @Description 删除操作
    * @param id
    */
    @GetMapping("delete/{id}")
    public ApiResult<User> delete(@PathVariable("id") Integer id) {
        userService.deleteById(id);
        return ApiResult.success();
    }
}
