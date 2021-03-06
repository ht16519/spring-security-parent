package com.xh.security.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Name ResponseUtil
 * @Description response工具类
 * @Author wen
 * @Date 2019-07-11
 */
@Slf4j
public class ResponseUtil {

    public static void write(Object result) {
        write(result, ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse());
    }

    public static void write(Object result, HttpServletResponse response) {
        write(result, response, HttpStatus.OK.value());
    }

    public static void write(Object result, HttpServletResponse response, int httpStatus) {
        response.setStatus(httpStatus);
        response.setCharacterEncoding(CharEncoding.UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try (PrintWriter writer = response.getWriter()) {
            writer.write(JsonUtil.serialize(result));
            writer.flush();
        } catch (IOException ex) {
            log.error("【Response响应服务】Web Response IOException:{}", ex);
        }
    }

}
