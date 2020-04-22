package com.xh.security.core.authentiation.oauth2.support.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Name AuthExceResult
 * @Description
 * @Author wen
 * @Date 2020-04-22
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthSimpleResult {

    private String msg;

    public static AuthSimpleResult build(String msg){
        return new AuthSimpleResult(msg);
    }



}
