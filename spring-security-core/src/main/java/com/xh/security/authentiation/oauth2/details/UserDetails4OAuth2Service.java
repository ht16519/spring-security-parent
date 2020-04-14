package com.xh.security.authentiation.oauth2.details;

import com.xh.security.authentiation.oauth2.support.model.AuthUser;
import com.xh.security.authentiation.oauth2.support.model.UserDetails4OAuth2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Name UserDetails4MobileService
 * @Description OAuth2.0登录服务类
 * @Author wen
 * @Date 2020-04-10
 */
public interface UserDetails4OAuth2Service {

    /** 通过提供商唯一凭证查询用户*/
    UserDetails4OAuth2 loadUserByProviderId(String providerId) throws UsernameNotFoundException;

    /**
    * @Name silenceRegister
    * @Description 静默注册方法
    * @Author wen
    * @Date 2020/4/14
    * @param authUser 提供从第三方获取到的用户信息
    * @return UserDetails 返回注册成功后的用户信息
    */
    UserDetails4OAuth2 silenceRegister(AuthUser authUser);
}
