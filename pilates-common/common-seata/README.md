# common-seata

#### 介绍
引用 spring-cloud-starter-alibaba-seata 排除了druid,解决和dubbo中的druid版本冲突


##### 使用方法
1. 添加依赖 common-seata
2. 在application.yml中添加配置 nacos:seata-client-config.yml?group=SEATA_GROUP 
3. 在需要开启全局事务的方法上加上注解@GlobalTransactional,被调用的方法不需要