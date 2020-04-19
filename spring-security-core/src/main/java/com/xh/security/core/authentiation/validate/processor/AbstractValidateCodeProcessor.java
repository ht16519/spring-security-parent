package com.xh.security.core.authentiation.validate.processor;

import com.xh.security.core.authentiation.oauth2.support.cache.AuthCache;
import com.xh.security.core.authentiation.validate.code.ValidateCode;
import com.xh.security.core.exception.ValidateCodeException;
import com.xh.security.core.utils.ValidateCodeUtil;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Name AValidateCodeProcessor
 * @Description 抽象验证码验证处理器
 * @Author wen
 * @Date 2020-04-10
 */
@Setter
public abstract class AbstractValidateCodeProcessor implements ValidateCodeProcessor{

    @Autowired
    private AuthCache authCache;

    /** 缓存的验证码key*/
    private String cacheCodeKey;

    /** 用户输入的验证码key*/
    private String inputCodeKey;

    @Override
    public void validate(HttpServletRequest request, HttpServletResponse response) throws ValidateCodeException {
        String inputCode = request.getParameter(inputCodeKey);
        if (StringUtils.isBlank(inputCode)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        //获取用户的缓存codeKey
        String onlyCacheCodeKey = ValidateCodeUtil.getKey(cacheCodeKey, request);
        ValidateCode validateCode = authCache.get(onlyCacheCodeKey, ValidateCode.class);
        if (null == validateCode || StringUtils.isBlank(validateCode.getCode())) {
            throw new ValidateCodeException("验证码不存在，请重新获取验证码");
        }
        if (validateCode.expire()) {
            authCache.remove(onlyCacheCodeKey);
            throw new ValidateCodeException("验证码已失效");
        }
        if (!StringUtils.equalsIgnoreCase(validateCode.getCode(), inputCode)) {
            throw new ValidateCodeException("验证码不正确");
        }
        authCache.remove(onlyCacheCodeKey);
    }

}
