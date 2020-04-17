package com.xh.demo.domain.auth;

import com.xh.security.core.authentiation.oauth2.support.model.SocialUserDetails;
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
public class SocialUserDetailsVo extends UserDetailsVo implements SocialUserDetails<Integer> {

    @Override
    public Integer getUserId() {
        return super.getId();
    }
}
