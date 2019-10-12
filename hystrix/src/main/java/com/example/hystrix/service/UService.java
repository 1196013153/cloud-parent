package com.example.hystrix.service;

import com.yqs.entity.User;

import java.util.List;

public interface UService {
    User find(Integer id);

    User[] findAll(List<Integer> ids);
}
