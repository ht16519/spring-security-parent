package com.xh.demo.domain.vo;

import com.xh.security.authentiation.oauth2.support.model.SocialUserDetails;
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
