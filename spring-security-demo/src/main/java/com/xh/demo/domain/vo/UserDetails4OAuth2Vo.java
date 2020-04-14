package com.xh.demo.domain.vo;

import com.xh.security.authentiation.oauth2.support.model.UserDetails4OAuth2;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Name UserDetailsVo
 * @Description
 * @Author wen
 * @Date 2020-04-09
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
public class UserDetails4OAuth2Vo extends UserDetailsVo implements UserDetails4OAuth2<Integer>{

    @Override
    public Integer getUserId() {
        return super.getId();
    }
}
