package com.example.feign.controller;

import com.example.feign.service.DemoService;
import com.example.feign.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {
    @Autowired
    HelloService helloService;
    @Autowired
    DemoService demoService;

    @RequestMapping("/feign/{name}")
    public String helloService(@PathVariable String name) {
        System.out.println(name);
        String hello = helloService.hello(name);
        return hello;
    }

    @RequestMapping("/demo")
    public String aaaaa() {
        return demoService.queryAll();
    }


}
