package com.example.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.yqs.entity.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class UserService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 异步调用，无效果，原因未知
     *
     * @param id
     * @return
     */
    @HystrixCommand
    public Future<User> getUserForAsync(Long id) {
        return new AsyncResult<User>() {
            @Override
            public User invoke() {
                System.out.println("获取user异步被调用了");
                return restTemplate.getForObject("http://EUREKA-PROVIDER/getUserById/{1}", User.class, id);
            }
        };
    }

    /**
     * hystrix异常处理---忽略异常
     *
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "fallback1", ignoreExceptions = NullPointerException.class)
    public User getUserIsException(Long id) {
        if (true) {
            throw new NullPointerException();
        }
        return restTemplate.getForObject("http://EUREKA-PROVIDER/getUserById/{1}", User.class, id);
    }

    public User fallback1(Long id, Throwable e) {
        System.out.println("------------------------------------");
        System.out.println("捕获异常" + e + "--------" + id);
        return new User();
    }

    /**
     * 加入缓存
     */
    @HystrixCommand
    @CacheResult(cacheKeyMethod = "getUserByIdCacheKey")
    public User getUserAddCache(Long id) {
        return restTemplate.getForObject("http://EUREKA-PROVIDER/getUserById/{1}", User.class, id);
    }

    /**
     * 获取缓存key
     *
     * @param id
     * @return
     */
    public String getUserByIdCacheKey(Long id) {
        System.out.println("缓存id=" + id);
        return String.valueOf(id);
    }

    @HystrixCollapser(batchMethod = "listUserByReqMerge", collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds", value = "100")
    })
    public User find(Integer id) {
        System.out.println("------------------------------------");
        return null;
    }
    @HystrixCommand
    public List<User> listUserByReqMerge(List<Integer> ids) {
        System.out.println("准备合并"+Thread.currentThread().getName());
        User[] users = restTemplate.getForObject("http://EUREKA-PROVIDER/listUsersByIds?ids={1}", User[].class, StringUtils.join(ids, ","));
        System.out.println(users.length+"----------");
        System.out.println(users[0]+"----------");
        return Arrays.asList(users);
    }
}
