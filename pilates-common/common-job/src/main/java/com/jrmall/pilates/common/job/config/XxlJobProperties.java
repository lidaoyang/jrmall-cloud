package com.jrmall.pilates.common.job.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * XXL Job 配置
 * <p>
 * 可以配置前缀为 xxl.job.admin 的配置
 *
 * @author: Dao-yang.
 * @date: Created in 2025/7/4 16:04
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "xxl.job.admin")
public class XxlJobProperties {

    /**
     * xxl-job admin address list, such as "http://address" or "http://address01,http://address02"
     */
    private String addresses = "http://127.0.0.1:8080/xxl-job-admin";
    ;

    /**
     * xxl-job, access token要和xxl-job-admin端一致
     */
    private String accessToken = "jrmall123";

    /**
     * xxl-job timeout by second, default 3s
     */
    private int timeout = 3;

}
