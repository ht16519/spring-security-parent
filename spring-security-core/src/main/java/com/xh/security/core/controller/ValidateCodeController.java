package com.xh.security.core.controller;

import com.xh.security.core.authentiation.oauth2.support.cache.AuthCache;
import com.xh.security.core.authentiation.oauth2.support.enums.AuthResponseStatus;
import com.xh.security.core.authentiation.oauth2.support.model.AuthResponse;
import com.xh.security.core.authentiation.validate.code.ImageCode;
import com.xh.security.core.authentiation.validate.code.ValidateCode;
import com.xh.security.core.authentiation.validate.generatator.ValidateCodeGenerator;
import com.xh.security.core.authentiation.validate.mobile.MobileValidator;
import com.xh.security.core.authentiation.validate.sms.SmsCodeSender;
import com.xh.security.core.consts.BeanNameConst;
import com.xh.security.core.consts.URLConst;
import com.xh.security.core.utils.JsonUtil;
import com.xh.security.core.utils.ValidateCodeUtil;
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
    @Autowired
    private AuthCache authCache;

    /**
     * 获取图形验证码
     */
    @GetMapping(URLConst.VALIDATE_IMAGE_CODE_PATH)
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.生成验证码
        ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(request);
        //2.存储验证码
        authCache.set(ValidateCodeUtil.setKey(BeanNameConst.CACHE_IMAGE_CODE_KEY, request, response),
                JsonUtil.serialize(ValidateCode.build(imageCode.getCode(), imageCode.getExpireTime())));
        //3.发送验证码
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    /**
     * 获取手机验证码
     */
    @GetMapping(URLConst.VALIDATE_SMS_CODE_PATH)
    public ResponseEntity<AuthResponse> smsCode(HttpServletRequest request, HttpServletResponse response) {
        //1.校验手机号码
        String mobile = request.getParameter("mobile");
        if (StringUtils.isBlank(mobile)) {
            return ResponseEntity.badRequest().body(AuthResponse.build(AuthResponseStatus.INVALID_EMPTY_MONBLE_PARAMETER));
        }
        if (!MobileValidator.isMobile(mobile)) {
            return ResponseEntity.badRequest().body(AuthResponse.build(AuthResponseStatus.INVALID_MOBILE_PARAMETER));
        }
        //2.存储生成的验证码
        ValidateCode validateCode = smsCodeGenerator.generate(request);
        authCache.set(ValidateCodeUtil.setKey(BeanNameConst.CACHE_MOBILE_CODE_KEY, request, response), JsonUtil.serialize(validateCode));
        //3.发送验证码
        smsCodeSender.send(mobile, validateCode.getCode());
        return ResponseEntity.ok().body(new AuthResponse(AuthResponseStatus.SUCCESS.getCode(), "短信验证码发送成功"));
    }

}
