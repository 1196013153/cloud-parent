package com.example.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("EUREKA-PROVIDER")
public interface HelloService {

    @RequestMapping("/hello")
    String hello();
}
