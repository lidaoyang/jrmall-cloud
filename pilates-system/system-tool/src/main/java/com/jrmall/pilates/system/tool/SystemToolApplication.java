package com.jrmall.pilates.system.tool;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@MapperScan(basePackages = {"com.jrmall.pilates.system.tool.*.mapper"})
@SpringBootApplication
public class SystemToolApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemToolApplication.class, args);
    }
}
