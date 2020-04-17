package com.xh.security.core.authentiation.validate.mobile.details;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Name UserDetails4MobileService
 * @Description 手机登录服务类
 * @Author wen
 * @Date 2020-04-10
 */
public interface UserDetails4MobileService {

    UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException;

}
