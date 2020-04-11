package com.xh.security.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Name SimpleResponse
 * @Description
 * @Author wen
 * @Date 2020-04-09
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleResponse {

    private Object msg;

    public static SimpleResponse build(Object msg) {
        return new SimpleResponse(msg);
    }

}
