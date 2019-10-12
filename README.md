# Spring Cloud

### 起步

#### 什么是微服务

​		微服务架构的系统是一个分布式的系统，按业务进行划分为独立的服务单元，解决单体系统的不足，同时也满足越来越复杂的业务需求。



##### 微服务vs传统开发

使用微服务这种开发模式和传统的开发模式对比，有很大的不同。

- 分工不同，以前我们可能是一个一个模块，现在可能是一人一个系统。
- 架构不同，服务的拆分是一个技术含量很高的问题，拆分是否合理对以后发展影响巨大。
- 部署方式不同，如果还像以前一样部署估计累死了，自动化运维不可不上。
- 容灾不同，好的微服务可以隔离故障避免服务整体down掉，坏的微服务设计仍然可以因为一个子服务出现问题导致连锁反应。
- 给数据库带来的挑战



##### **服务拆分原则**

横向拆分：按照不同的业务域进行拆分，如订单，营销，支付，积分等

纵向拆分：把一个业务里不同的模块或组件进行拆分。



##### **拆分的几种方式**

- 按照并发量进行拆分
- 按照业务的改的情况进行拆分，把很少变动的部分抽离出去，降低维护成本
- 按照安全级别进行拆分，降低对防火墙等方面的并发要求



#### **Spring Cloud简介**

​		微服务是可以独立部署、水平扩展、独立访问（或者有独立的数据库）的服务单元，Spring Cloud就是这些微服务的大管家，采用了微服务这种架构之后，项目的数量会非常多，Spring Cloud做为大管家需要管理好这些微服务，自然需要很多组件来帮忙。

​		Spring Cloud是一个基于Spring Boot实现的微服务架构工具。它为微服务架构中涉及的配置管理、服务治理、断路器、智能路由、微代理、控制总线、全局锁、分布式会话和集群状态管理提供了一种简单的开发方式。

**核心思想：**服务拆分解耦，降低复杂性，强调将功能进行合理拆解，尽可能保证每个服务功能单一，将各个服务做轻，灵活，可复用。

**特点：**

- **约定大于配置**
- **组件丰富，功能齐全**
- **高内聚低耦合：**单一职责，轻量级通信方式。
- **高度自治：**能独立开发、部署、发布，进程隔离。
- **以业务为中心：**每个服务代表了特定的业务逻辑，能更快地响应业务的变化，围绕业务组织团队。
- **弹性设计：**容错，降级



##### Spring Cloud架构

![](Spring Cloud文档.assets/diagram-distributed-systems.svg)

​																			<!--来自spring官网-->



下图是个人认为比较好的介绍springcloud的架构图

<img src="Spring Cloud文档.assets/3b2d315f6bfda769b93e10723a62dc5d279.jpg"  />



##### **Spring Cloud和Dubbo对比**

Dubbo底层使用Netty这样的NIO框架，是基于TCP协议传输，RPC通信

Spring Cloud基于Http+Rest接口通信

Http请求会有更大的报文，占的带宽也会更多，但rest相比RPC更加简单，灵活



##### Spring Cloud的不足

- 运维要求较高
- 分布式固有的复杂性
- 接口调整成本高（修改某一微服务的API，所有引用都要调整）



#### Spring Cloud子项目

- **Spring Cloud Config：** 配置管理工具，支持使用git存储配置内容，可以使用它实现应用配置的外部化存储，并支持客户端配置信息刷新、加密、解密配置内容等。
- **Spring Cloud Netflix：**核心组件，对多个Netflix OSS开源套件进行整合。
  - **Eureka：** 服务治理组件，包含服务注册中心、服务注册和发现机制的实现
  - **Hystrix：**容错管理组件，实现断路器模式，帮助服务依赖中出现的延迟和为故障提供强大的容错能力
  - **Ribbon：**客户端负载均衡的服务调用组件
  - **Feign：**基于Ribbon和Hystrix的声明式服务调用组件
  - **Zuul：**网关组件，提供智能路由，访问过滤等功能（实际为一系列的过滤器）
- **Spring Cloud Bus：**事件、消息总线，用于传播集群中的状态变化或事件，以触发后续的处理，比如用来动态刷新配置等，支持 Kafka 和 RabbitMQ
- **Spring Cloud Stream：**数据流操作开发包，封装Redis,Rabbit、Kafka等发送接收消息。可以通过简单的声明式模型来发送和接收消息
- **Spring Cloud Cluster：**针对ZooKeeper、Redis、consul的选举算法和通用状态模式的实现
- **Spring Cloud Consul**：consul是一个服务发现与配置工具，与Docker容器可以无缝集成。
- **Spring Cloud Security：**安全工具包，提供在Zuul代理中对OAuth2客户端请求的中继器
- **Spring Cloud Sleuth：**日志收集工具包，Spring Cloud应用的分布式跟踪实现
- **Spring Cloud ZooKeeper：**基于ZooKeeper的服务发现与配置管理组件
- **Spring Cloud Starters：**Spring Cloud基础组件，基于Spring Boot风格项目的基础依赖模块
- **Spring Cloud CLI：**基于 Spring Boot CLI，让你以命令行方式快速建立云组件。
- ............

#### 版本说明

​		Spring Cloud由于有多个子项目，所以没有采用版本号的方式，而是通过命名方式控制版本

如Angel.SR6	Brixton.SR5	Camden.M1			Angel为版本名（A,B,C,D开头...），SRn为版本号，

目前最新版为 H。

引入Spring Cloud版本依赖

```java
 <dependencyManagement>
 	<dependencies>
    	<dependency>
        	<groupId>org.springframework.cloud</groupId>
        	<artifactId>spring-cloud-dependencies</artifactId>
        	<version>${spring-cloud.version}</version>
        	<type>pom</type>
        	<scope>import</scope>
        </dependency>
    </dependencies>
 </dependencyManagement>
```



### Spring Cloud Netflix

#### Eureka

##### 基础架构

​	eureka基础架构包含三个核心要素：

- **服务注册中心：**Eureka提供的服务端，提供服务注册和发现的功能。
- **服务提供者：**提供服务的应用，可以是Spring Boot应用，也可以是其它平台遵守Eureka通信机制的应用。
- **服务消费者：**消费者从服务注册中心获取注册列表，从而使消费者知道在何处调用服务。

##### 服务治理机制

如下图所示

![](Spring Cloud文档.assets/1517538693389814.png)

##### 服务配置

导入依赖：

```java
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

**注册中心配置**

在springboot项目入口类中添加注解@EnableEurekaServer声明为注册中心

```javascript
#禁用客户端注册行为
eureka.client.register-with-eureka=false
eureka.client.fetch-registery=false
    
#关闭注册中心的自我保护（开发时建议，防止关闭的实例无法被注册中心剔除）
eureka.server.enable-self-preservation: false
```

**多个注册中心实现高可用**

只需将defaultZone指向其他的注册中心即可

```javascript
#注册中心1
client:
    serviceUrl:
      defaultZone: http://peer2:1112/eureka/
```

**客户端配置**

```javascript
#设置当前实例的主机名称
eureka.instance.hostname=localhost
	
#指定服务注册中心地址，类型为 HashMap，并设置有一组默认值，默认的Key为 defaultZone；默认的Value为 #http://localhost:8761/eureka ，如果服务注册中心为高可用集群时，多个注册中心地址以逗号分隔。

eureka.client.service-url.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/

#如果服务注册中心加入了安全验证，这里配置的地址格式为： http://<username>:<password>@localhost:8761/eureka 其中 <username> 为安全校验的用户名；<password> 为该用户的密码

#是否优先使用ip地址作为主机的标识符
enreka.instance.prefer-ip-address=true
```

##### 服务提供者

- **服务注册**：配置文件eureka.client.register-with-eureka=true（默认开启）将自己注册到Eureka，同时会附带自身的一些元数据。
- **服务同步**：如服务治理机制中的图所示，有多台Eureka注册中心时，客户端的信息会被多台注册中心维护，当服务向注册中心发送注册请求时，它会将请求转发给其它注册中心，实现信息同步。
- **服务续约**：注册完服务后，服务提供者会维护一个心跳用来持续告诉Eureka自己还活着，防止Eureka的“剔除任务”将该服务清理出去。

服务续约的两个属性配置

```javascript
#服务续约任务的执行间隔（每隔多久发送心跳）
eureka.instance.lease-renewal-interval-in-seconds=30
#服务失效时间（超过多长时间没有心跳就将服务剔除出去）
eureka.instance.lease-expiration-duration-in-seconds=90
```

##### 服务消费者

- 获取服务：会向注册中心发送请求，获取服务清单。需要保证`eureka.client.register-with-eureka=true（默认为ture）`，若希望修改缓存清单更新间隔，可以通过`eureka.client.registry-fetch-interval-seconds=30`进行修改
- 服务调用：获取服务清单后，可以根据获取的服务实例名和ribbon来轮询调用服务，实现客户端的负载均衡。
- 服务下线：服务正常关闭后，会向注册中心发送下线请求，注册中心将该服务状态设置下线（DOWN）。

##### 服务注册中心

- 失效剔除：服务非正常退出（如内存溢出，网络故障等），注册中心并未收到下线请求，Eureka默认每隔一段时间（60s）将超时（默认90s）没有续约的服务清理出去。

- 自我保护：在本地开发时，常会出现下面的警告信息

  <font color="red">EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.</font>

  这就是触发了保护机制，Eureka统计在15分钟内心跳失败比例是否低于85%，低于则会进行保护。那么客户端调用很容易出现调用失败情况，所以客户端必须有容错机制。本地开发可以使用下面配置关闭自我保护：

  `enreka.server.enable-self-preservation=false`

##### 客户端配置说明

客户端在启动类上添加@EnableEurekaClient注解即可

注意：spring cloud中discovery service有许多种实现（eureka、consul、zookeeper等等），@EnableDiscoveryClient基于spring-cloud-commons, @EnableEurekaClient基于spring-cloud-netflix。
其实用更简单的话来说，就是如果选用的注册中心是eureka，那么就推荐@EnableEurekaClient，如果是其他的注册中心，那么推荐使用@EnableDiscoveryClient。

注意：无论是提供者还是消费者本质都是客户端，需在启动类上加@EnableDiscoveryClient注解



#### Ribbon

##### 基础架构

​		对于整个的WEB端的构架（SpringBoot实现）可以轻松方便的进行WEB程序的编写，而后利用Nginx或Apache实现负载均衡处理，但是你WEB端出现了负载均衡，那么业务端呢？应该也提供有多个业务端进行负载均衡。那么这个时候就需要将所有需要参与到负载均衡的业务端在Eureka之中进行注册。



![](Spring Cloud文档.assets/738818-20180713143544008-1379755906.jpg)



**Ribbon是客户端的负载均衡，nginx是服务端的负载均衡。**最大的不同的在于：

​		客户端负载均衡中，所有客户端节点都维护着自己要访问的服务端清单，而这些服务端清单来自于注册中心。同服务端负载均衡架构类似，在客户端负载均衡中也需要心跳去维护服务端清单的健康性，只是需要和注册中心配合。



##### **代码示例**

**提供者：**

例如提供一个RequestMapping接口，接受请求

```java
@RequestMapping("/hello")
    public String index(String name) {
        logger.info("hello,name:"+name);
        return "hello world";
    }
```

**消费者：**

```java
	//创建restTemplate实例
    @Bean
    @LoadBalanced		//开启客户端的负载均衡
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

	@Autowired
    RestTemplate restTemplate;

    @RequestMapping("/ribbon-hello")
    public String hello(String name) {
        return restTemplate.getForEntity("http://EUREKA-CLIENT/hello?name="+name, 					String.class).getBody();
    }
```

##### RestTemplate常用api

getForEntity(String url,Class responseType,Object urlVariables)

url：服务提供者的地址，如“http://EUREKA-SERVER/user?name={1}”

responseType：返回响应体包装的类型

urlVariables：向url提供参数

------

post，put，delete只需改为postForEntity(String url,Class responseType,Object urlVariables)   ...........

......................其它可以通过百度谷歌自行查找，这里不在做介绍

##### 重试机制

由于Eureka的保护机制和剔除服务需要一定的延迟时间，所以我们需要加入重试机制来增强对这类问题容错。

以对服务hello-service的调用为例，可以在配置文件中加入下列内容

```javascript
#开启重试机制，默认为false
spring.cloud.loadbalancer.retry.enabled=true
#断路器超时时间需要大于Ribbon的超时时间，否则不会触发重试
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=10000
#请求连接的超时时间
hello-service.ribbon.ConnectTimeout=250
#请求处理的超时时间
hello-service.ribbon.ReadTimeout=1000
#对所有请求都进行重试
hello-service.ribbon.OkToRetryOnAllOperations=true
#切换实例的重试次数
hello-service.ribbon.MaxAutoRetriesNextServer=2
#对当前实例的重试次数
hello-service.ribbon.MaxAutoRetries=1
```

根据如上配置，当访问到故障请求时，它会在尝试访问一次当前实例（次数由MaxAutoRetries配置），如果不行，就换一个实例访问，还不行再换一个实例访问（次数由MaxAutoRetriesNextServer配置），如果依然不行，返回错误信息。

#### Hystrix

##### 基础架构

​		在进行整体的微架构设计的时候由于牵扯的问题还是属于RPC，所以必须考虑熔断处理机制，实际上所有的熔断就好比生活之中使用保险丝一样，有了保险丝在一些设备出现了故障之后依然可以保护家庭的电器可以正常使用，如果说现在有若干的微服务，并且这些微服务之间可以相互调用，例如A微服务调用了B微服务，B微服务调用了C微服务。

​		如果在实际的项目设计过程之中没有处理好熔断机制，那么就会产生雪崩效应，所以为了防止这样的问题出现，SpringCloud里面提供有一个Hystrix熔断处理机制，以保证某一个微服务即使出现了问题之后依然可以正常使用。

![](Spring Cloud文档.assets/hystrix-command-flow-chart-640.png)

​															<!--来自hystrix官方-->



**hystrix流程图**

![1569215536920](Spring Cloud文档.assets/1569215536920.png)

![1569215825823](Spring Cloud文档.assets/1569215825823.png)

执行流程

![](Spring Cloud文档.assets/1222062593-578b40230bde1_articlex.png)



##### 

##### 代码示例

```java
//controller
@Autowired
HelloService helloService;

@RequestMapping("/hystrix")
public String ribbon() {
    return helloService.helloService();
}
---------------------------------------------------------
//helloService
@Autowired
private RestTemplate restTemplate;

@HystrixCommand(fallbackMethod = "helloFallBack")
public String helloService() {
    return restTemplate.getForEntity("http://EUREKA-PROVIDER/hello", 			 				String.class).getBody();
}

public String helloFallBack() {
        return "error";
    }

```

##### 特性

**服务熔断：**

​		一般是某个服务故障或异常引起，类似“保险丝”，当某个异常被触发，直接熔断整个服务，而不是等到此服务超时。 断路器保持在开路状态一段时间后(默认5秒), 自动切换到半开路状态(HALF-OPEN).

这时会判断下一次请求的返回情况, 如果请求成功, 断路器切回闭路状态(CLOSED), 否则重新切换到开路状态(OPEN). Hystrix的断路器就像我们家庭电路中的保险丝, 一旦后端服务不可用, 断路器会直接切断请求链, 避免发送大量无效请求影响系统吞吐量, 并且断路器有自我检测并恢复的能力.

**服务降级：**

降级是在客户端，与服务端无关。

降级，从整体负荷考虑，某个服务熔断后，服务器将不再被调用，此时客户端可以自己准备一个本地的fallback回调，这样，虽然服务水平下降，但可用，比直接挂掉要好。 

**依赖隔离：**

​		在Hystrix中, 主要通过线程池来实现资源隔离. 通常在使用的时候我们会根据调用的远程服务划分出多个线程池.比如说，一个服务调用两外两个服务，你如果调用两个服务都用一个线程池，那么如果一个服务卡在哪里，资源没被释放后面的请求又来了，导致后面的请求都卡在哪里等待，导致你依赖的A服务把你卡在哪里，耗尽了资源，也导致了你另外一个B服务也不可用了。这时如果依赖隔离，某一个服务调用A B两个服务，如果这时我有100个线程可用，我给A服务分配50个，给B服务分配50个，这样就算A服务挂了，我的B服务依然可以用。

**请求缓存：**

​		比如一个请求过来请求我userId=1的数据，你后面的请求也过来请求同样的数据，这时我不会继续走原来的那条请求链路了，而是把第一次请求缓存过了，把第一次的请求结果返回给后面的请求。

**请求合并：**

​		我依赖于某一个服务，我要调用N次，比如说查数据库的时候，我发了N条请求发了N条SQL然后拿到一堆结果，这时候我们可以把多个请求合并成一个请求，发送一个查询多条数据的SQL的请求，这样我们只需查询一次数据库，提升了效率。

##### 监控

![](Spring Cloud文档.assets/微信图片_20190923120040.jpg)



#### Feign

​		在进行客户端使用Rest架构调用的时候，往往都需要一个调用地址，即使现在使用了Eureka作为注册中心，那么它也需要有一个明确的调用地址，可是所有的操作如果都利用调用地址的方式来处理，程序的开发者最方便应用的工具是接口，所以现在就希望可以将所有的Rest服务的内容以接口的方式出现调用，所以它又提供了一个Feign技术，利用此技术可以伪造接口实现。

![1568968191793](Spring Cloud文档.assets/1568968191793.png)

**基本用法，代码示例：**

```java
//接受请求，提供服务
 @RequestMapping("/hello")
    public String hello(String name) throws InterruptedException {
        return name;
    }
-----------------------------------------
//调用服务
//service
@FeignClient("EUREKA-PROVIDER")
public interface HelloService {    
	@RequestMapping("/hello")    
	String hello();
}
//controller
@Autowired
HelloService helloService;
@RequestMapping("/feign")
public String helloService() {
   return helloService.hello();
}
```

使用继承特性简化代码量

![1569213294706](Spring Cloud文档.assets/1569213294706.png)

##### 关于feign的思考，继承特性的优缺点

​		使用feign继承特性的优点很明显，可以将接口的定义从controller中剥离，同时配合Maven仓库就可以轻易地实现接口定义的共享，实现在构建期的接口绑定，从而有效减少服务客户端的绑定配置。这么做虽然可以很方便的实现接口定义和依赖的共享，不用再复制粘粘接口进行绑定，但是这样的做法使用不当的话会带来副作用。由于接口在构建期就建立了依赖，那么接口的变动就会对项目构建造成影响，可能服务提供方修改了接口定义，那么会直接导致客户端工程构建失败。所以，如果开发团队通过此方法来实现接口共享的话，建议在开发评审期间严格遵守面向对象的开闭原则，尽可能的做好前后版本的兼容，防止牵一发而动全身的后果，增加团队不必要的维护工作量。



#### Zuul

##### 简介

​		Zuul是所有从设备和web站点到Netflix流媒体应用程序后端的请求的前门。作为一个边缘服务应用程序，Zuul是为了支持动态路由、监视、弹性和安全性。它还可以根据需要将请求路由到多个Amazon自动伸缩组。

Zuul使用了一系列不同类型的过滤器，使我们能够快速灵活地将功能应用到edge服务中。这些过滤器帮助我们执行以下功能:

> - 身份验证和安全性——识别每个资源的身份验证需求并拒绝不满足这些需求的请求。
> - 洞察和监控——在边缘跟踪有意义的数据和统计数据，以便为我们提供准确的生产视图。
> - 动态路由——根据需要动态地将请求路由到不同的后端集群。
> - 压力测试——逐步增加集群的流量，以评估性能。
> - 减少负载——为每种类型的请求分配容量，并删除超过限制的请求。
> - 静态响应处理——直接在边缘构建一些响应，而不是将它们转发到内部集群
> - 多区域弹性——跨AWS区域路由请求，以使我们的ELB使用多样化，并使我们的优势更接近我们的成员

​		通过Zuul的代理用户只需要知道指定的路由的路径就可以访问指定的微服务的信息，这样更好的提现了java中的“key=value”的设计思想，而且所有的微服务通过zuul进行代理之后也更加合理的进行名称隐藏。

![](Spring Cloud文档.assets/1202638-20180519140957813-1257463746.png)

##### 工作原理

在高级视图中，Zuul 2.0是一个Netty服务器，它运行预过滤器(入站过滤器)，然后使用Netty客户机代理请求，然后在运行后过滤器(出站过滤器)后返回响应。
![架构图](https://segmentfault.com/img/remote/1460000018826273)

过滤器是Zuul业务逻辑的核心所在。它们能够执行非常大范围的操作，并且可以在请求-响应生命周期的不同部分运行，如上图所示。

- Inbound Filters在路由到源之前执行，可以用于身份验证、路由和装饰请求。
- Endpoint Filters 可用于返回静态响应，否则内置的ProxyEndpoint过滤器将把请求路由到源。
- Outbound Filters 在从源获取响应后执行，可用于度量、装饰用户响应或添加自定义头。

还有两种类型的过滤器:**同步和异步**。因为我们是在一个事件循环上运行的，所以**千万不要阻塞过滤器**。如果要阻塞，可以在一个异步过滤器中阻塞，在一个单独的threadpool上阻塞——否则可以使用同步过滤器。

##### 使用示例：

在启动类上加@EnableZuulProxy

配置文件：

```java
zuul:
  routes:
    api-a:			//自定义拦截名称
      path: /api-a/**		//拦截路径
      serviceId: EUREKA-PROVIDER	 //转发到的服务
server.port=8085
```

当访问http://localhost:8085/api-a/hello会自动转发到EUREKA-PROVIDER的hello请求中

​		

### Spring Cloud Config

​		在SpringBoot学习的时候一直强调过一个问题：在SpringBoot里面强调的是一个“零配置”的概念，本质在于不需要配置任何的配置文件，但是事实上这一点并没有完全的实现，因为在整个在整体的实际里面，依然会提供有application.yml配置文件，那么如果在微服务的创建之中，那么一定会有成百上千个微服务的信息出现，于是这些配置文件的管理就成为了问题。例如：现在你突然有一天你的主机要进行机房的变更，所有的服务的IP地址都可能发生改变，这样对于程序的维护是非常不方便的，为了解决这样的问题，在Spring Cloud设计的时候提供有一个Spring Cloud Config的程序组件，利用这个组件就可以直接基于GIT或者SVN来进行配置文件的管理。

![](Spring Cloud文档.assets/9866e2210fab3260af081dafa8fb5ed0.png)

**服务端配置：**application.yml

```java
spring:
  application:
    name: config-server
  cloud:
    config:
      server:
        git:
          uri: //git仓库地址
          username: 
          password: 
```

**客户端配置：**配置在**bootstrap.yml**中(比application先加载，会覆盖application的内容)

```java
spring:
  application:
    name: //此处为配置文件前缀名，如custom-dev.yml，则此处为custom
  cloud:
    config:
      uri: http://localhost:6060
      profile: dev			//custom-dev.yml
      label: //所在git分支
```



### Spring Cloud Stream

待完成.........

### Spring Cloud Sleuth

##### 基本术语

Spring Cloud Sleuth采用的是Google的开源项目Dapper的专业术语。

**Span：**基本工作单元，发送一个远程调度任务 就会产生一个Span，Span是一个64位ID唯一标识的，Trace是用另一个64位ID唯一标识的，Span还有其他数据信息，比如摘要、时间戳事件、Span的ID、以及进度ID。
**Trace：**一系列Span组成的一个树状结构。请求一个微服务系统的API接口，这个API接口，需要调用多个微服务，调用每个微服务都会产生一个新的Span，所有由这个请求产生的Span组成了这个Trace。
**Annotation：**用来及时记录一个事件的，一些核心注解用来定义一个请求的开始和结束 。这些注解包括以下：
**cs** - Client Sent -客户端发送一个请求，这个注解描述了这个Span的开始
**sr** - Server Received -服务端获得请求并准备开始处理它，如果将其sr减去cs时间戳便可得到网络传输的时间。
**ss** - Server Sent （服务端发送响应）–该注解表明请求处理的完成(当请求返回客户端)，如果ss的时间戳减去sr时间戳，就可以得到服务器请求的时间。
**cr** - Client Received （客户端接收响应）-此时Span的结束，如果cr的时间戳减去cs时间戳便可以得到整个请求所消耗的时间。

##### 实现跟踪

给需要监控的服务导入依赖

```java
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-sleuth</artifactId>
</dependency>
```

此时sleuth已经完成对服务的控制，日志打印会出现sleuth相关信息。



##### Zipkin简介

​		Zipkin是Twitter的一个开源项目，它基于Google Dapper实现。我们可以使用它来收集各个服务器上请求链路的跟踪数据，并通过它提供的REST API接口来辅助我们查询跟踪数据以实现对分布式系统的监控程序，从而及时地发现系统中出现的延迟升高问题并找出系统性能瓶颈的根源。除了面向开发的API接口之外，它也提供了方便的UI组件来帮助我们直观的搜索跟踪信息和分析请求链路明细，比如：可以查询某段时间内各用户请求的处理时间等。

[![img](http://blog.didispace.com/assets/zipkin-architecture.png)](http://blog.didispace.com/assets/zipkin-architecture.png)

上图展示了Zipkin的基础架构，它主要有4个核心组件构成：

- Collector：收集器组件，它主要用于处理从外部系统发送过来的跟踪信息，将这些信息转换为Zipkin内部处理的Span格式，以支持后续的存储、分析、展示等功能。
- Storage：存储组件，它主要对处理收集器接收到的跟踪信息，默认会将这些信息存储在内存中，我们也可以修改此存储策略，通过使用其他存储组件将跟踪信息存储到数据库中。
- RESTful API：API组件，它主要用来提供外部访问接口。比如给客户端展示跟踪信息，或是外接系统访问以实现监控等。
- Web UI：UI组件，基于API组件实现的上层应用。通过UI组件用户可以方便而有直观地查询和分析跟踪信息。



##### 使用zipkin提供链路跟踪

官方推荐直接使用jar包启动。

在需要提供日志跟踪服务的配置文件中添加：

```java
spring:
    zipkin:  
		base-url: http://ip:port  (zipkin的网络地址)  
	sleuth:  
		sampler:   
			probability: 1.0
```



### Actuator

​		Actuator 监控分成两类：原生端点和用户自定义端点；自定义端点主要是指扩展性，用户可以根据自己的实际应用，定义一些比较关心的指标，在运行期进行监控。

原生端点是在应用程序里提供众多 Web 接口，通过它们了解应用程序运行时的内部状况。原生端点又可以分成三类：

- 应用配置类：可以查看应用在运行期的静态信息：例如自动配置信息、加载的 springbean 信息、yml 文件配置信息、环境信息、请求映射信息；
- 度量指标类：主要是运行期的动态信息，例如堆栈、请求连、一些健康指标、metrics 信息等；
- 操作控制类：主要是指 shutdown,用户可以发送一个请求将应用的监控功能关闭。

Actuator 提供了 13 个接口，具体如下表所示。

| HTTP 方法 | 路径            | 描述                                                         |
| --------- | --------------- | ------------------------------------------------------------ |
| GET       | /auditevents    | 显示应用暴露的审计事件 (比如认证进入、订单失败)              |
| GET       | /beans          | 描述应用程序上下文里全部的 Bean，以及它们的关系              |
| GET       | /conditions     | 就是 1.0 的 /autoconfig ，提供一份自动配置生效的条件情况，记录哪些自动配置条件通过了，哪些没通过 |
| GET       | /configprops    | 描述配置属性(包含默认值)如何注入Bean                         |
| GET       | /env            | 获取全部环境属性                                             |
| GET       | /env/{name}     | 根据名称获取特定的环境属性值                                 |
| GET       | /flyway         | 提供一份 Flyway 数据库迁移信息                               |
| GET       | /liquidbase     | 显示Liquibase 数据库迁移的纤细信息                           |
| GET       | /health         | 报告应用程序的健康指标，这些值由 HealthIndicator 的实现类提供 |
| GET       | /heapdump       | dump 一份应用的 JVM 堆信息                                   |
| GET       | /httptrace      | 显示HTTP足迹，最近100个HTTP request/repsponse                |
| GET       | /info           | 获取应用程序的定制信息，这些信息由info打头的属性提供         |
| GET       | /logfile        | 返回log file中的内容(如果 logging.file 或者 logging.path 被设置) |
| GET       | /loggers        | 显示和修改配置的loggers                                      |
| GET       | /metrics        | 报告各种应用程序度量信息，比如内存用量和HTTP请求计数         |
| GET       | /metrics/{name} | 报告指定名称的应用程序度量值                                 |
| GET       | /scheduledtasks | 展示应用中的定时任务信息                                     |
| GET       | /sessions       | 如果我们使用了 Spring Session 展示应用中的 HTTP sessions 信息 |
| POST      | /shutdown       | 关闭应用程序，要求endpoints.shutdown.enabled设置为true       |
| GET       | /mappings       | 描述全部的 URI路径，以及它们和控制器(包含Actuator端点)的映射关系 |
| GET       | /threaddump     | 获取线程活动的快照                                           |



## Docker

### 什么是docker

docker是一个容器：容器就是将软件打包成标准化单元，以用于开发、交付和部署。

- 容器镜像是轻量的、可执行的独立软件包 ，包含软件运行所需的所有内容：代码、运行时环境、系统工具、系统库和设置。
- 容器化软件适用于基于Linux和Windows的应用，在任何环境中都能够始终如一地运行。
- 容器赋予了软件独立性，使其免受外在环境差异（例如，开发和预演环境的差异）的影响，从而有助于减少团队间在相同基础设施上运行不同软件时的冲突。

### **为什么要用Docker**

- Docker的镜像提供了除内核外完整的运行时环境，确保了应用运行环境一致性，从而不会再出现“这段代码在我机器上没问题啊”这类问题；——  一致的运行环境
- 可以做到秒级、甚至毫秒级的启动时间。大大的节约了开发、测试、部署的时间。——更快速的启动时间
- 避免公用的服务器，资源会容易受到其他用户的影响。——隔离性
- 善于处理集中爆发的服务器使用压力；——弹性伸缩，快速扩展
- 可以很轻易的将在一个平台上运行的应用，迁移到另一个平台上，而不用担心运行环境的变化导致应用无法正常运行的情况。——迁移方便
- 使用Docker可以通过定制应用镜像来实现持续集成、持续交付、部署。——持续交付和部署