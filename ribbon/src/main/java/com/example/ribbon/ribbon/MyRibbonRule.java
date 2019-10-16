package com.example.ribbon.ribbon;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/***
 * 自定义轮询规则
 * @author Administrator
 */
//@Component
public class MyRibbonRule implements IRule {
    private ILoadBalancer iLoadBalancer;

    @Override
    public Server choose(Object key) {
        List<Server> servers = iLoadBalancer.getAllServers();
        for (Server server : servers) {
            System.out.println(server);
        }
        return servers.get(0);
    }

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        this.iLoadBalancer = lb;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return iLoadBalancer;
    }
}
