package com.xh.security.authentiation.processor;

import com.xh.security.authentiation.code.ValidateCode;
import com.xh.security.exception.ValidateCodeException;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Name AValidateCodeProcessor
 * @Description
 * @Author wen
 * @Date 2020-04-10
 */
@Setter
public abstract class AbstractValidateCodeProcessor implements ValidateCodeProcessor{

    /** 缓存的验证码key*/
    private String cacheCodeKey;

    /** 用户输入的验证码key*/
    private String inputCodeKey;

    @Override
    public void validate(HttpServletRequest request) throws ValidateCodeException {
        HttpSession session = request.getSession();
        String inputCode = request.getParameter(inputCodeKey);
        if (StringUtils.isBlank(inputCode)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        ValidateCode sessionCode = (ValidateCode) session.getAttribute(cacheCodeKey);
        if (null == sessionCode || StringUtils.isBlank(sessionCode.getCode())) {
            throw new ValidateCodeException("验证码不存在，请重新获取验证码");
        }
        if (sessionCode.isExpire()) {
            session.removeAttribute(cacheCodeKey);
            throw new ValidateCodeException("验证码已失效");
        }
        if (!StringUtils.equalsIgnoreCase(sessionCode.getCode(), inputCode)) {
            throw new ValidateCodeException("验证码不正确");
        }
        session.removeAttribute(cacheCodeKey);
    }

}
