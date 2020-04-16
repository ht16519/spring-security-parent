package com.xh.security.controller;

import com.xh.security.authentiation.oauth2.support.enums.AuthResponseStatus;
import com.xh.security.authentiation.oauth2.support.exception.AuthException;
import com.xh.security.authentiation.oauth2.support.model.AuthResponse;
import com.xh.security.authentiation.validate.mobile.MobileValidator;
import com.xh.security.consts.KeyConst;
import com.xh.security.consts.URLConst;
import com.xh.security.authentiation.validate.code.ImageCode;
import com.xh.security.authentiation.validate.code.ValidateCode;
import com.xh.security.authentiation.validate.generatator.ValidateCodeGenerator;
import com.xh.security.authentiation.validate.sms.SmsCodeSender;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Name ValidateCodeController
 * @Description 验证码生成控制层
 * @Author wen
 * @Date 2020-04-09
 */
@RestController
public class ValidateCodeController {

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;
    @Autowired
    private ValidateCodeGenerator smsCodeGenerator;
    @Autowired
    private SmsCodeSender smsCodeSender;

    /**
     * 获取图形验证码
     */
    @GetMapping(URLConst.VALIDATE_IMAGE_CODE_PATH)
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(request);
        request.getSession().setAttribute(KeyConst.CACHE_IMAGE_CODE_KEY, ValidateCode.build(imageCode.getCode(), imageCode.getExpireTime()));
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    /**
     * 获取手机验证码
     */
    @GetMapping(URLConst.VALIDATE_SMS_CODE_PATH)
    public ResponseEntity<AuthResponse> smsCode(HttpServletRequest request) {
        //1.生成短信验证码并保存到session
        ValidateCode validateCode = smsCodeGenerator.generate(request);
        request.getSession().setAttribute(KeyConst.CACHE_MOBILE_CODE_KEY, validateCode);
        //2.发送短信验证码
        String mobile = request.getParameter("mobile");
        if (StringUtils.isBlank(mobile)) {
            return ResponseEntity.badRequest().body(AuthResponse.build(AuthResponseStatus.INVALID_EMPTY_MONBLE_PARAMETER));
        }
        if (!MobileValidator.isMobile(mobile)) {
            return ResponseEntity.badRequest().body(AuthResponse.build(AuthResponseStatus.INVALID_MOBILE_PARAMETER));
        }
        smsCodeSender.send(mobile, validateCode.getCode());
        return ResponseEntity.ok().body(new AuthResponse(AuthResponseStatus.SUCCESS.getCode(), "短信验证码发送成功"));
    }

}
