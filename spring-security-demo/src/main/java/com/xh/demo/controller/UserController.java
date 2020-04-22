package com.xh.demo.controller;

import com.xh.demo.domain.po.User;
import com.xh.demo.domain.vo.ApiResult;
import com.xh.demo.service.UserService;
import com.xh.security.core.properties.SecurityProperties;
import com.xh.security.core.utils.UserDetailsUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * @param id
     * @Name get
     * @Description 通过id获取数据
     */
    @GetMapping("{id}")
    public ApiResult<User> get(@PathVariable("id") Integer id) {
        return ApiResult.success(userService.getById(id));
    }

    @GetMapping("jwt")
    public ApiResult<String> jwt(@AuthenticationPrincipal String username, HttpServletRequest request) throws Exception {
        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "bearer ");

        Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2Support().getJwtSigningKey().getBytes("UTF-8"))
                .parseClaimsJws(token).getBody();
        Integer id = (Integer) claims.get("id");
        String nickname = (String) claims.get("nickname");
        String avatar = (String) claims.get("avatar");
        Integer sex = (Integer) claims.get("sex");
        return ApiResult.success(username);
    }

    @GetMapping("me")
    public ApiResult<String> me(@AuthenticationPrincipal String username) throws Exception {
        log.info("@AuthenticationPrincipal 注解获取的 username:{}", username);
        log.info("Authentication 中获取的 username:{}", UserDetailsUtil.getUsername());
        return ApiResult.success();
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
