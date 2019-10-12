package com.example.hystrix.config;

import com.example.hystrix.interceptor.HystrixInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Administrator
 */
public class MySpringMVCConfig implements WebMvcConfigurer {
    @Autowired
    private HystrixInterceptor hystrixInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(hystrixInterceptor).addPathPatterns("/**");
    }
}
