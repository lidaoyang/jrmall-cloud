# common-seata

#### 介绍

引用 spring-cloud-starter-alibaba-seata 排除了druid,解决和dubbo中的druid版本冲突

##### 使用方法

1. 添加依赖 common-seata
2. 在application.yml中添加配置 nacos:seata-client-config.yml?group=SEATA_GROUP
3. 在需要开启全局事务的方法上加上注解@GlobalTransactional,被调用的方法不需要

```yaml 导入nacos配置
spring:
  cloud:
    nacos:
      username: ${nacos.username}
      password: ${nacos.password}
      # 配置中心
      config:
        server-addr: ${nacos.server-addr}
        namespace: ${nacos.spring-cloud.config.namespace}
  config:
    import:
      - nacos:seata-client-config.yml?group=SEATA_GROUP

nacos:
  username: nacos
  password: nacos
  server-addr: 172.16.85.108:8848
  spring-cloud:
    config:
      namespace: pilates-cloud-dev
```