package com.example.ribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {
    @Autowired
    private RestTemplate restTemplate;

   // @HystrixCommand(fallbackMethod = "helloFallBack")
    public String helloService(String name) {
        return restTemplate.getForEntity("http://EUREKA-PROVIDER/hello?name={name}", String.class,name).getBody();
    }

    public String helloFallBack() {
        return "error";
    }
}
