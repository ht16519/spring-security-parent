package com.xh.security.controller;

import com.xh.security.consts.KeyConst;
import com.xh.security.consts.URLConst;
import com.xh.security.support.SimpleResponse;
import com.xh.security.authentiation.code.ImageCode;
import com.xh.security.authentiation.code.ValidateCode;
import com.xh.security.authentiation.generatator.ValidateCodeGenerator;
import com.xh.security.authentiation.sms.SmsCodeSender;
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
        BufferedImage image = imageCode.getImage();
        imageCode.setImage(null);
        request.getSession().setAttribute(KeyConst.CACHE_IMAGE_CODE_KEY, imageCode);
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    /**
     * 获取手机验证码
     */
    @GetMapping(URLConst.VALIDATE_SMS_CODE_PATH)
    public ResponseEntity<SimpleResponse> smsCode(HttpServletRequest request) {
        //1.生成短信验证码并保存到session
        ValidateCode validateCode = smsCodeGenerator.generate(request);
        request.getSession().setAttribute(KeyConst.CACHE_MOBILE_CODE_KEY, validateCode);
        //2.发送短信验证码
        String mobile = request.getParameter("mobile");
        if (StringUtils.isBlank(mobile)) {
            return ResponseEntity.badRequest().body(SimpleResponse.build("请输入手机号码"));
        }
        if (!mobile.matches("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$")) {
            return ResponseEntity.badRequest().body(SimpleResponse.build("手机号码不正确"));
        }
        smsCodeSender.send(mobile, validateCode.getCode());
        return ResponseEntity.ok().body(SimpleResponse.build("短信验证码发送成功"));
    }

}
