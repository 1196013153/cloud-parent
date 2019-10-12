package com.example.feign.controller;

import com.example.feign.service.ApiService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController implements ApiService {
    @Override
    public String one() {
        return "one";
    }
    @Override
    public String two() {
        return "two";
    }
    @Override
    public String three() {
        return "three";
    }
}
