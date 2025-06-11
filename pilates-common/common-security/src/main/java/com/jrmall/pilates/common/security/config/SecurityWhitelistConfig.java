package com.jrmall.pilates.common.security.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 白名单配置
 * @author: Dao-yang.
 * @date: Created in 2023/7/10 18:10
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "custom.security")
public class SecurityWhitelistConfig {

    private List<String> whitelistPaths;
}
