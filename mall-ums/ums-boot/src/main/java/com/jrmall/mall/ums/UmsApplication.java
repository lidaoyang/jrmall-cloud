package com.jrmall.mall.ums;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDubbo(scanBasePackages = {"com.jrmall.mall.ums.provider"})
@MapperScan(basePackages = {"com.jrmall.mall.ums.mapper"})
@SpringBootApplication
@EnableDiscoveryClient
public class UmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(UmsApplication.class, args);
    }
}
