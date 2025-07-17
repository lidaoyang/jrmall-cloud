# jrmall-cloud

#### 介绍
面向 SpringBoot3，基于 Java17、Spring Cloud&Alibaba 2023、Spring Boot 3.2.9 、Dubbo 3.3.4 全新升级OAuth2授权+微服务+UMS管理系统解决方案。


#### Spring Authorization Server 授权码模式测试流程
1. 创建名为 jrmall_cloud 的数据库用户和相关数据库，依次执行docs/sql/*.sql的脚本创建表
2. 启动nacos，创建 cloud-namespace-id 的namespace，导入docs/nacos/DEFAULT_GROUP.zip配置
3. 依次启动 system-provider 、auth、system-boot、gateway
4. 文档地址： http://127.0.0.1:9999/doc.html juru/juru2013

#### 注意: 每个模块运行所需环境变量如下
##### system-provider: 
命令行参数 ``-Dspring.profiles.active=dev -Dproject.name=system-provider``
```properties
ALI_CLOUD_ACCESS_KEY_ID=AccessKeyId1234;
ALI_CLOUD_ACCESS_KEY_SECRET=AccessKeySecret1234;
MYSQL_DB_NAME=jr_system;
MYSQL_HOST=127.0.0.1;
MYSQL_PASSWORD=123456;
MYSQL_USERNAME=jrmall_cloud;
REDIS_HOST=127.0.0.1;
REDIS_PASSWORD=123456
```
###### auth:
命令行参数 ``-Dspring.profiles.active=dev -Dproject.name=auth``
```properties
ALI_CLOUD_ACCESS_KEY_ID=AccessKeyId1234;
ALI_CLOUD_ACCESS_KEY_SECRET=AccessKeySecret1234;
MYSQL_DB_NAME=oauth2_server;
MYSQL_HOST=127.0.0.1;
MYSQL_PASSWORD=123456;
MYSQL_USERNAME=jrmall_cloud;
REDIS_HOST=127.0.0.1;
REDIS_PASSWORD=123456
```
###### system-boot:
命令行参数 ``-Dspring.profiles.active=dev -Dproject.name=system-boot``
```properties
REDIS_HOST=127.0.0.1;
REDIS_PASSWORD=123456
```
###### system-tool:
命令行参数 ``-Dspring.profiles.active=dev -Dproject.name=system-tool``
```properties
ALI_CLOUD_ACCESS_KEY_ID=AccessKeyId1234;
ALI_CLOUD_ACCESS_KEY_SECRET=AccessKeySecret1234;
MYSQL_DB_NAME=jr_system;
MYSQL_HOST=127.0.0.1;
MYSQL_PASSWORD=123456;
MYSQL_USERNAME=jrmall_cloud;
REDIS_HOST=127.0.0.1;
REDIS_PASSWORD=123456
```
###### gateway:
命令行参数  ``-Dspring.profiles.active=dev -Dproject.name=gateway -Dcsp.sentinel.app.type=1``
```properties
REDIS_HOST=127.0.0.1;
REDIS_PASSWORD=123456
```
###### mall-ums:
命令行参数 ``-Dspring.profiles.active=dev -Dproject.name=mall-ums``
```properties
JOB_ACCESS_TOKEN=jrmall123
ALI_CLOUD_ACCESS_KEY_ID=AccessKeyId1234;
ALI_CLOUD_ACCESS_KEY_SECRET=AccessKeySecret1234;
MYSQL_DB_NAME=mall_ums;
MYSQL_HOST=127.0.0.1;
MYSQL_PASSWORD=123456;
MYSQL_USERNAME=jrmall_cloud;
REDIS_HOST=127.0.0.1;
REDIS_PASSWORD=123456
```

###### xxl-job-admin:
命令行参数 ``-Dspring.profiles.active=dev -Dproject.name=xxl-job-admin``
```properties
JOB_ACCESS_TOKEN=jrmall123;
MAIL_FROM=jrmall1234@qq.com;
MAIL_PASSWORD=123456;
MAIL_USERNAME=jrmall1234@qq.com;
MYSQL_DB_NAME=xxl_job;
MYSQL_HOST=127.0.0.1;
MYSQL_PASSWORD=123456;
MYSQL_USERNAME=xxl_job
```
###### xxl-job-executor:
命令行参数 ``-Dspring.profiles.active=dev -Dproject.name=xxl-job-executor``
```properties
JOB_ACCESS_TOKEN=jrmall123;
```



#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request