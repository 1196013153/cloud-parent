package com.example.hystrix.command;

import com.example.hystrix.service.UService;
import com.example.hystrix.service.UserService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.yqs.entity.User;

import java.util.List;

/**
 * @author Administrator
 */
public class UserCommand extends HystrixCommand<User[]> {
    private UService uService;
    List<Integer> ids;
    public UserCommand(UService uService, List<Integer> ids) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CollapsingGroup")).andCommandKey(HystrixCommandKey.Factory.asKey("CollapsingKey")));
        this.uService = uService;
        this.ids = ids;
    }

    @Override
    protected User[] run() throws Exception {
        return uService.findAll(ids);
    }
}
