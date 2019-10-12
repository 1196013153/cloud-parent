package com.example.hystrix.command;

import com.example.hystrix.service.UService;
import com.example.hystrix.service.UserService;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import com.yqs.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserCollapseCommand extends HystrixCollapser<User[], User,Integer> {
    private UService uService;
    private Integer id;

    public UserCollapseCommand(UService uService, Integer id) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("UserCollapseCommand"))
        .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100)));
        this.uService = uService;
        this.id = id;
    }
    @Override
    public Integer getRequestArgument() {
        return id;
    }

    @Override
    protected HystrixCommand<User[]> createCommand(Collection<CollapsedRequest<User, Integer>> collapsedRequests) {
        List<Integer> ids = new ArrayList<>(5);
        ids.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
        UserCommand stringBatchCommand = new UserCommand(uService,ids);
        return stringBatchCommand;
    }

    @Override
    protected void mapResponseToRequests(User[] batchResponse, Collection<CollapsedRequest<User, Integer>> collapsedRequests) {
        int count = 0;
        for (CollapsedRequest<User, Integer> collapsedRequest : collapsedRequests) {
            User user = batchResponse[count++];
            collapsedRequest.setResponse(user);
        }
    }
}
