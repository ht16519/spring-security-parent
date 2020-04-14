package com.xh.security.utils;

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

    public static void writer(Object result) {
        writer(result, ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse());
    }

    public static void writer(Object result, HttpServletResponse response) {
        writer(result, response, HttpStatus.OK.value());
    }

    public static void writer(Object result, HttpServletResponse response, int httpStatus) {
        response.setStatus(httpStatus);
        response.setCharacterEncoding(CharEncoding.UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try (PrintWriter writer = response.getWriter()) {
            writer.write(JsonUtil.serialize(result));
            writer.flush();
        } catch (IOException ex) {
            log.error("Web Response IOException:{}", ex);
        }
    }

}
