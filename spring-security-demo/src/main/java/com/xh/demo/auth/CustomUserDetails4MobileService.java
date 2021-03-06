package com.xh.demo.auth;

import com.xh.demo.domain.po.User;
import com.xh.demo.domain.auth.UserDetailsVo;
import com.xh.demo.service.UserService;
import com.xh.security.core.authentiation.validate.mobile.details.UserDetails4MobileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Name CustomUserDetails4MobileService
 * @Description
 * @Author wen
 * @Date 2020-04-10
 */
@Slf4j
@Component
public class CustomUserDetails4MobileService implements UserDetails4MobileService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        log.info("【登录认证服务】用户：{}手机登录操作", mobile);
        UserDetailsVo userDetailsVo = new UserDetailsVo();
        User user = userService.getByMobile(mobile);
        if (null == user) {
            throw new UsernameNotFoundException("手机号码不存在");
        }
        BeanUtils.copyProperties(user, userDetailsVo);
        return userDetailsVo;
    }
}
