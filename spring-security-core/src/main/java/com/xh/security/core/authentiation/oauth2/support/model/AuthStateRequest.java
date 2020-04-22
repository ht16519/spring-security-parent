package com.xh.security.core.authentiation.oauth2.support.model;

import com.xh.security.core.authentiation.oauth2.support.request.AuthRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Name AuthStateRequest
 * @Description
 * @Author wen
 * @Date 2020-04-22
 */
@Getter
@Setter
@NoArgsConstructor
public class AuthStateRequest {

    private AuthRequest authRequest;

    private String userId;
}
