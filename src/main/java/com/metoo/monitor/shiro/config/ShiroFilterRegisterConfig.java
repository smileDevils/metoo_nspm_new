package com.metoo.monitor.shiro.config;

import com.metoo.monitor.shiro.filter.MyAccessControlFilter;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ShiroFilterRegisterConfig {

//    @Bean
////    public FilterRegistrationBean myFilter(){
////        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
////        filterRegistrationBean.setFilter(new MyAccessControlFilter());
////        filterRegistrationBean.setUrlPatterns(Arrays.asList("/monitor/systemInfo"));
////        return filterRegistrationBean;
////    }
}
