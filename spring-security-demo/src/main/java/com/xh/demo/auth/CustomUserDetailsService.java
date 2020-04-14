package com.xh.demo.auth;

import com.xh.demo.domain.po.User;
import com.xh.demo.domain.vo.UserDetailsVo;
import com.xh.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Name CustomUserDetailsService
 * @Description 自定义用户名密码登录服务类
 * @Author wen
 * @Date 2020-04-09
 */
@Slf4j
@Component("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO 验证用户输入账号格式的逻辑
        log.info("【登录认证服务】用户：{}表单登录操作", username);
        UserDetailsVo userDetailsVo = new UserDetailsVo();
        User user = userService.getByUserName(username);
        if(null == user){
            throw new UsernameNotFoundException("用户不存在");
        }
        BeanUtils.copyProperties(user, userDetailsVo);
        return userDetailsVo;
    }

}
