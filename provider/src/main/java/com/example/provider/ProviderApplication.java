package com.example.provider;

import com.yqs.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class ProviderApplication {

    private Logger logger = LoggerFactory.getLogger(getClass());
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }

    @RequestMapping("/hello")
    public String hello(String name) throws InterruptedException {
       // Thread.sleep(300);
      //  logger.info("服务被调用,name="+name);
        return name;
    }

    @RequestMapping("/listUsers")
    public List<User> listUser() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "zhangsan", new Date(), "123456"));
        users.add(new User(2, "lisi", new Date(), "789"));
        users.add(new User(3, "wangwu", new Date(), "123"));
        return users;
    }
    @RequestMapping("/listUsersByIds")
    public List<User> listUserByIds(int[] ids) {
        logger.info("请求合并生效"+Thread.currentThread().getName());
        List<User> users = new ArrayList<>(5);
        if (ids.length == 0) {
            return null;
        }
        for (int id : ids) {
            users.add(new User(id, "zhangsan" + id, new Date(), "123456"));
        }
        logger.info(users.toString());
        return users;
    }

    @RequestMapping("/getUserById/{id}")
    public User getUserById(@PathVariable int id) {
        logger.info("getUserById被调用了");
        return new User(id, "zhaoliu", new Date(), "12345678");
    }
}
