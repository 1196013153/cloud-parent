package com.example.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("ORGANIZATION")
public interface DemoService {
    @RequestMapping("/resource/all")
    String queryAll();
}
