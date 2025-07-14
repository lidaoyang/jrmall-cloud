/*

package com.jrmall.cloud.auth.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.nio.charset.StandardCharsets;


*/
/**
 * 默认安全配置
 *//*

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class SecurityConfig {


    private final CustomSecurityProperties customSecurityProperties;

    */
/**
     * Spring Security 安全过滤器链配置
     *
     * @param http 安全配置
     * @return 安全过滤器链
     *//*


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
                                                   HandlerMappingIntrospector introspector) throws Exception {

        // 登录页面配置
        http.formLogin(
                formLogin -> {
                    formLogin.loginPage("/login");

                    // 如果是绝对路径，表示是前后端分离的登录页面，需要重写登录成功和失败为 JSON 格式
                    if (UrlUtils.isAbsoluteUrl(customSecurityProperties.getLoginUrl())) {
                        formLogin.successHandler((request, response, authentication) -> {
                            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.getWriter().write(JSONUtil.toJsonStr(Result.success()));
                        });
                        formLogin.failureHandler((request, response, exception) -> {
                            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.getWriter().write(JSONUtil.toJsonStr(Result.failed(ResultCode.AUTHORIZED_ERROR, exception.getMessage())));
                        });
                    }
                }
        );

        return http.build();
    }

}

*/
