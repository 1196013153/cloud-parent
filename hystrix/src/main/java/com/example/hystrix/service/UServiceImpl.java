package com.example.hystrix.service;

import com.yqs.entity.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class UServiceImpl implements UService {
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public User find(Integer id) {
        return restTemplate.getForObject("http://EUREKA-PROVIDER/getUserById?ids={1}",
                User.class, id);
    }

    @Override
    public User[] findAll(List<Integer> ids) {
        return restTemplate.getForObject("http://EUREKA-PROVIDER/listUsersByIds?ids={1}", User[].class, StringUtils.join(ids, ","));
    }
}
