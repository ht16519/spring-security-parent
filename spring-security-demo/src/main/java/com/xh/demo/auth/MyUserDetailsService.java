package com.xh.demo.auth;

import com.xh.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Name MyUserDetailsService
 * @Description
 * @Author wen
 * @Date 2020-04-09
 */
@Slf4j
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.xh.demo.domain.po.User user = userService.getByUserName(username);
        log.info("用户={}登录操作", username);
        if(null == user){
            throw new UsernameNotFoundException("用户不存在");
        }
        return new User(username, user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
