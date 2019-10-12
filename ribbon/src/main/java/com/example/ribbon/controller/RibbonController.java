package com.example.ribbon.controller;

import com.example.ribbon.config.RestTemplateConfig;
import com.example.ribbon.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RibbonController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    HelloService helloService;

    @RequestMapping("/ribbon-get")
    public String ribbon() {
        log.info("ribbon 被调用");
        return helloService.helloService();
    }
}
