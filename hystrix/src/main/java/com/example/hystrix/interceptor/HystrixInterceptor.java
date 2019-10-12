package com.example.hystrix.interceptor;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 */
@Component
public class HystrixInterceptor implements HandlerInterceptor {
    Integer i = 0;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        System.out.println("执行控制器之前执行拦截器");
        if (i==0) {
            HystrixRequestContext.initializeContext();
            i++;
        }
        return true;
    }

}
