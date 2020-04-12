package com.xh.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Name DruidConfig
 * @Description
 * @Author wen
 * @Date 2019-06-16
 */
@Configuration
public class DruidConfig {

    private static final String REQUEST_URLS = "/druid/*";

    private static final String FILTER_EXCLUSIONS = "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*";

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druid() {
        return new DruidDataSource();
    }

    //配置Druid的监控
    /**
     * 1、配置一个管理后台的Servlet
     **/
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), REQUEST_URLS);
        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", "root");
        initParams.put("loginPassword", "123456");
//        initParams.put(KeyConst.DURID_REQUEST_ALLOW_IP_KEY, "127.0.0.1");//允许访问ip
//        initParams.put("deny",""); //拒绝访问ip
        bean.setInitParameters(initParams);
        return bean;
    }

    /**
     * 2、配置一个web监控的filter
     **/
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", FILTER_EXCLUSIONS);
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }

}
