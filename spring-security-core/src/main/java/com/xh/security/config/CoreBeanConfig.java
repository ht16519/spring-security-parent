package com.xh.security.config;

import com.xh.security.properties.SecurityProperties;
import com.xh.security.validate.generatator.ImageCodeGenerator;
import com.xh.security.validate.generatator.SimpleCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Name BeanConfig
 * @Description
 * @Author wen
 * @Date 2020-04-10
 */
@Configuration
public class CoreBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(ImageCodeGenerator.class)
    public ImageCodeGenerator imageCodeGenerator() {
        SimpleCodeGenerator imageCodeGenerator = new SimpleCodeGenerator();
        imageCodeGenerator.setSecurityProperties(securityProperties);
        return imageCodeGenerator;
    }

}
