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

    private Object content;

    public static SimpleResponse build(Object content) {
        return new SimpleResponse(content);
    }

}
