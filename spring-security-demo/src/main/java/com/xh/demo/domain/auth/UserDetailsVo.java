package com.xh.demo.domain.auth;

import com.xh.security.core.authentiation.oauth2.support.model.details.AuthUserDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Date;

/**
 * @Name UserDetailsVo
 * @Description
 * @Author wen
 * @Date 2020-04-09
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDetailsVo implements AuthUserDetails<Integer> {

    private Integer id;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户最后一次登录时间
     */
    private Date lastLogin;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态（-1：冻结，10：正常）
     */
    private Integer status;

    /**
     *
     */
    private String username;

    /**
     *
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 性别（1，0：男：女）
     */
    private Integer sex;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_MEMBER,ROLE_USER");
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status == 10;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void eraseCredentials() {
        password = null;
    }

    @Override
    public String toString() {
        return this.username;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }

    @Override
    public Integer getUserId() {
        return this.getId();
    }

}
