package com.jrmall.pilates.common.security.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 白名单配置
 *
 * @author: Dao-yang.
 * @date: Created in 2023/7/10 18:10
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "custom.security")
public class SecurityWhitelistConfig {

    /**
     * 全局白名单路径
     */
    private List<String> whitelistPaths = new ArrayList<>();

    public List<String> getWhitelistPaths() {
        if (appWhitelistPaths != null && !appWhitelistPaths.isEmpty()) {
            whitelistPaths.addAll(appWhitelistPaths);
        }
        return whitelistPaths;
    }

    /**
     * 本应用的白名单路径
     */
    private List<String> appWhitelistPaths;
}
