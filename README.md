[TOC]
# spring session redis 
spring session 实现了HttpSession 的完全透明化的分布式实现。spring session支持多种策略（一个浏览器一个或多个session）,cookie方式存储session,http header存储session
## 步骤
1. 增加spring session 依赖
```xml
    <dependency>
        <groupId>org.springframewBork.session</groupId>
        <artifactId>spring-session-data-redis</artifactId>
        <version>1.1.1.RELEASE</version>
    </dependency>
```

2. 增加spring session配置
```xml
    <bean class="org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration">
        <property name="maxInactiveIntervalInSeconds" value="60" />         <!--session超时时间-->
    </bean>
    <!--链接redis的工厂-->
    <bean class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory">
        <property name="hostName" value="127.0.0.1" />                              <!--redis 主机-->
        <property name="port" value="6379" />                                               <!--redis 端口-->
        <property name="password" value="" />                                               <!--redis 密码-->
        <property name="database" value="0" />                                             <!--redis 数据库-->
        <property name="usePool" value="true" />                                          <!--是否使用链接池-->
        <property name="poolConfig" ref="jedisPoolConfig" />                        <!--连接池引用-->
    </bean>

    <!--redis链接池-->
    <bean id="jedisPoolConfig" class="redis.clients.jedis.JedisPoolConfig">
        <property name="maxIdle" value="3" />                                             <!--采用common pool对象池，具体配置参数查看common pool-->
        <property name="maxTotal" value="10" />
        <property name="minIdle" value="1" />
        <property name="maxWaitMillis" value="10000" />
    </bean>
```
3. 在web.xml中增加spring session过滤器
```xml
    <filter>
        <filter-name>springSessionRepositoryFilter</filter-name>
        <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>springSessionRepositoryFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```

## 集群
spring session redis 目前只支持redis官方的的集群方案sentinel，spring配置参数如下
```xml
    <bean class="org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration">
        <property name="maxInactiveIntervalInSeconds" value="60" />         <!--超时时间-->
    </bean>

    <!--集群配置-->
    <bean id="redisSentinelConfiguration" class="org.springframework.data.redis.connection.RedisSentinelConfiguration">
        <property name="master">
            <bean class="org.springframework.data.redis.connection.RedisNode">
                <property name="name" value="mymaster"/>
            </bean>
        </property>
        <property name="sentinels">
            <set>
                <bean class="org.springframework.data.redis.connection.RedisNode">
                    <constructor-arg name="host" value="127.0.0.1"/>
                    <constructor-arg name="port" value="26379"/>
                </bean>
                <bean class="org.springframework.data.redis.connection.RedisNode">
                    <constructor-arg name="host" value="127.0.0.1"/>
                    <constructor-arg name="port" value="26380"/>
                </bean>
                <bean class="org.springframework.data.redis.connection.RedisNode">
                    <constructor-arg name="host" value="127.0.0.1"/>
                    <constructor-arg name="port" value="26381"/>
                </bean>
            </set>
        </property>
    </bean>

    <bean id="jeidsConnectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory">
        <constructor-arg ref="redisSentinelConfiguration"/>
        <property name="database" value="4" />
        <property name="usePool" value="true" />
        <property name="poolConfig" ref="jedisPoolConfig" />
    </bean>

    <!--redis链接池-->
    <bean id="jedisPoolConfig" class="redis.clients.jedis.JedisPoolConfig">
        <property name="maxIdle" value="3" />
        <property name="maxTotal" value="10" />
        <property name="minIdle" value="1" />
        <property name="maxWaitMillis" value="10000" />
    </bean>
```

## 测试
1. 启动 redis-session模块
2. 访问http://localhost:8080/main
> 跳转至登陆页面

3. 输入用户名密码登陆
4. 查看redis数据
```shell
127.0.0.1:6379> keys *
1) "spring:session:sessions:expires:724e8caa-9870-44ff-a131-cbfcd6e60fed"
2) "spring:session:sessions:724e8caa-9870-44ff-a131-cbfcd6e60fed"
3) "spring:session:sessions:expires:6ebd1abd-11b7-4f0a-b89b-c556365b02cb"
4) "spring:session:sessions:6ebd1abd-11b7-4f0a-b89b-c556365b02cb"
5) "spring:session:expirations:1459182480000"
```


# 示例
示例代码请参考目录HD_基于Redist的Session共享实现/sample-spring-session.zip

# 待完善
以配置参数形式做spring session redis 和 web容器session切换

