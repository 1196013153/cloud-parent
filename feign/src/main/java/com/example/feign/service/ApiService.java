package com.example.feign.service;

import org.springframework.web.bind.annotation.RequestMapping;

public interface ApiService {
    @RequestMapping("/one")
    String one();
    @RequestMapping("/two")
    String two();
    @RequestMapping("/three")
    String three();
}
