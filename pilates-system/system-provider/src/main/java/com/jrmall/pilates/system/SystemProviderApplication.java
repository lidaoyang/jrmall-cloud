package com.jrmall.pilates.system;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDubbo
@EnableTransactionManagement // 开启事务
@MapperScan(basePackages = {"com.jrmall.pilates.system.mapper"})
@SpringBootApplication
public class SystemProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemProviderApplication.class, args);
    }
}
