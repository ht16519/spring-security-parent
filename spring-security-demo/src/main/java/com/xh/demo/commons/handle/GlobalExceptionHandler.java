package com.xh.demo.commons.handle;

import com.xh.security.core.authentiation.oauth2.support.model.AuthSimpleResult;
import com.xh.security.core.exception.AuthenticationBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import com.xh.demo.domain.vo.ExResult;
import com.xh.demo.commons.enums.MessageEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.xh.demo.commons.exception.BusinessException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name GlobalException
 * @Description 全局异常处理器
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExResult> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        log.error("【业务处理异常】：错误码={}，错误信息={}", ex.getCode(), ex.getMsg());
        return ResponseEntity.ok().body(ExResult.build(ex));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExResult> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        log.error("【业务处理异常】：错误信息={}", ex.getMessage());
        return ResponseEntity.ok().body(ExResult.build(MessageEnum.REQUEST_URL_NOT_SUPPORTED));
    }

    @ExceptionHandler(AuthenticationBusinessException.class)
    public ResponseEntity<AuthSimpleResult> handleBusinessException(AuthenticationBusinessException ex, HttpServletRequest request) {
        String message = ex.getMessage();
        log.error("【业务处理异常】：错误信息={}", message);
        return ResponseEntity.badRequest().body(AuthSimpleResult.build(message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExResult> handleException(Exception ex, HttpServletRequest request) {
        log.error("【系统处理异常】：错误信息={}", ex);
        return ResponseEntity.ok().body(ExResult.build(MessageEnum.REQUEST_LIMITED));
    }

}
