package com.xh.sso.client01.controller;

import com.xh.sso.client01.domain.auth.UserVo;
import com.xh.sso.client01.domain.po.User;
import com.xh.sso.client01.domain.vo.ApiResult;
import com.xh.sso.client01.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SecurityProperties securityProperties;


    @GetMapping("me")
    public ApiResult<Authentication> me(@AuthenticationPrincipal String username, Authentication authentication) throws Exception {
        log.info("@AuthenticationPrincipal 注解获取的 username:{}", username);
//        log.info("Authentication 中获取的 username:{}", UserDetailsUtil.getUsername());
        return ApiResult.success(authentication);
    }

    @PostMapping("me")
    public ApiResult<String> me() {
        return ApiResult.success("新增操作成功");
    }

    @GetMapping("info")
    public ApiResult<UserVo> userInfo(@AuthenticationPrincipal UserVo userVo) {
        return ApiResult.success(userVo);
    }

    @GetMapping("id")
    public ApiResult<Object> userInfo(@AuthenticationPrincipal(expression = "#this.id") Object id) {
        return ApiResult.success(id);
    }

    /**
     * @param user
     * @Name add
     * @Description 保存操作
     */
    @PostMapping("save")
    public ApiResult save(@RequestBody User user) {
        //添加操作
        return ApiResult.res(userService.add(user));
    }

    /**
     * @param user
     * @Name update
     * @Description 修改操作
     */
    @PostMapping("update")
    public ApiResult update(@RequestBody User user) {
        //修改操作
        return ApiResult.res(userService.update(user));
    }

    /**
     * @param id
     * @Name delete
     * @Description 删除操作
     */
    @GetMapping("delete/{id}")
    public ApiResult<User> delete(@PathVariable("id") Integer id) {
        userService.deleteById(id);
        return ApiResult.success();
    }
}
