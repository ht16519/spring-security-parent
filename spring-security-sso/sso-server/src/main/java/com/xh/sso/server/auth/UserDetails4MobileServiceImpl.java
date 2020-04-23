package com.xh.sso.server.auth;

import com.xh.sso.server.domain.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Name UserDetails4MobileServiceImpl
 * @Description
 * @Author wen
 * @Date 2020-04-23
 */
@Slf4j
@Component
public class UserDetails4MobileServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserVo.builder().id(111L).username(username)
                .password(passwordEncoder.encode("111"))
                .build();
    }

}
