package com.example.hystrix.controller;

import com.example.hystrix.command.UserCollapseCommand;
import com.example.hystrix.command.UserCommand;
import com.example.hystrix.service.UService;
import com.example.hystrix.service.UserService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.yqs.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static com.netflix.hystrix.HystrixCommand.*;

@RestController
public class UserController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private UService uService;
    @RequestMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) throws Exception {
        System.out.println(id);
        return restTemplate.getForObject("http://EUREKA-PROVIDER/getUserById/{1}", User.class, id);
    }
    @RequestMapping("/userAsync/{id}")
    public User getUserAsync(@PathVariable Long id) throws Exception {
        System.out.println("准备异步调用");
        User user = userService.getUserForAsync(id).get();
        System.out.println(user);
        return user;
    }
    @RequestMapping("/userIsException/{id}")
    public User getUserIsException(@PathVariable Long id) throws Exception {
        User user = userService.getUserIsException(id);
        return user;
    }
    @RequestMapping("/userAddCache/{id}")
    public User getUserAddCache(@PathVariable Long id) throws Exception {
        HystrixRequestContext.initializeContext();
        User user = userService.getUserAddCache(id);
        userService.getUserAddCache(id);
        userService.getUserAddCache(id);
        userService.getUserAddCache(id);
        return user;
    }
    @RequestMapping("/userReqMergeTest")
    public void userReqMergeTest() throws InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        User user = userService.find(2);
        System.out.println(user);
        User user1 = userService.find(44);
        System.out.println(user1);
        Thread.sleep(1000);
        userService.find(89384);
        context.close();
    }
    @RequestMapping("/userReqMergeTestByExtend")
    public User userReqMergeTestByExtend() throws Exception {
        HystrixRequestContext.initializeContext();
        UserCollapseCommand command = new UserCollapseCommand(uService,1);
        UserCollapseCommand command2 = new UserCollapseCommand(uService,2);
        UserCollapseCommand command3 = new UserCollapseCommand(uService,12);
        User execute = command.execute();
        command2.execute();
        command3.execute();
        System.out.println(execute);
        return new User();
    }
}
