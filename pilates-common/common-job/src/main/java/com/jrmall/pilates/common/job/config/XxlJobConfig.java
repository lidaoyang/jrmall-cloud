package com.jrmall.pilates.common.job.config;


import lombok.Data;

/**
 * XXL Job 配置
 * <p>
 * 可以配置前缀为 xxl.job.admin 的配置
 *
 * @author: Dao-yang.
 * @date: Created in 2025/7/4 16:04
 */
@Data
public class XxlJobConfig {

    private static final String DEFAULT_ADDRESSES = "http://127.0.0.1:8080/xxl-job-admin";
    private static final String DEFAULT_ACCESS_TOKEN = "jrmall123";

    /**
     * 默认超时时间3秒
     */
    private static final int DEFAULT_TIMEOUT = 3;

    public XxlJobConfig() {
        this(DEFAULT_ADDRESSES, DEFAULT_ACCESS_TOKEN, DEFAULT_TIMEOUT);
    }

    /**
     * 创建一个默认的配置
     *
     * @param addresses   调度中心地址
     * @param accessToken 调度中心API访问令牌
     */
    public XxlJobConfig(String addresses, String accessToken) {
        this(addresses, accessToken, DEFAULT_TIMEOUT);
    }

    /**
     * 创建一个默认的配置
     *
     * @param addresses   调度中心地址
     * @param accessToken 调度中心API访问令牌
     * @param timeout     调度中心API访问超时时间
     */
    public XxlJobConfig(String addresses, String accessToken, int timeout) {
        this.addresses = addresses;
        this.accessToken = accessToken;
        this.timeout = timeout;
    }

    private String addresses;

    private String accessToken;

    private int timeout;

}
