package com.jrmall.pilates.common.security.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域共享配置
 *
 *
 * @author haoxr
 * @since 2024/3/14
 */
@Configuration
public class CorsConfig {

    @Resource
    private SecurityWhitelistConfig securityWhitelistConfig;

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin(securityWhitelistConfig.getCorsOrigin());

        // 设置跨域访问可以携带cookie //当allowCredentials为true时，allowdOrigins不能包含特殊值“*”，因为无法在“访问控制允许Origin”响应标头上设置该值
        configuration.setAllowCredentials(true);
        // 允许所有的请求方法 ==> GET POST PUT Delete
        configuration.addAllowedMethod("*");
        // 允许携带任何头信息
        configuration.addAllowedHeader("*");

        // 初始化cors配置源对象
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();

        // 给配置源对象设置过滤的参数
        // 参数一: 过滤的路径 == > 所有的路径都要求校验是否跨域
        // 参数二: 配置类
        configurationSource.registerCorsConfiguration("/**", configuration);

        // 返回配置好的过滤器
        return new CorsFilter(configurationSource);

    }



}
