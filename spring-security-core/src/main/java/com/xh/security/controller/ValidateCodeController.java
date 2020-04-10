package com.xh.security.controller;

import com.xh.security.consts.KeyConst;
import com.xh.security.consts.URLConst;
import com.xh.security.validate.code.ImageCode;
import com.xh.security.validate.generatator.ImageCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ImageCodeGenerator imageCodeGenerator;

    @GetMapping(URLConst.VALIDATE_IMAGE_CODE_PATH)
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = imageCodeGenerator.generate(request);
        BufferedImage image = imageCode.getImage();
        imageCode.setImage(null);
        request.getSession().setAttribute(KeyConst.IMAGE_CODE_KEY, imageCode);
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

}
