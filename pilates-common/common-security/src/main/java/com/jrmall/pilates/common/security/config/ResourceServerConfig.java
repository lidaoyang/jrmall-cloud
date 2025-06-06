package com.jrmall.pilates.common.security.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.jrmall.pilates.common.constant.JwtClaimConstants;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;

/**
 * 资源服务器配置
 *
 * @author haoxr
 * @since 3.0.0
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // 开启方法权限控制
@RequiredArgsConstructor
@Slf4j
public class ResourceServerConfig {

    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;


    /**
     * 白名单路径列表
     */
    @Value("${security.whitelist-paths}")
    private List<String> whitelistPaths;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        log.info("whitelist path:{}", JSONUtil.toJsonStr(whitelistPaths));
        http.authorizeHttpRequests((requests) ->
                        {
                            if (CollectionUtil.isNotEmpty(whitelistPaths)) {
                                for (String whitelistPath : whitelistPaths) {
                                    requests.requestMatchers(mvcMatcherBuilder.pattern(whitelistPath)).permitAll();
                                }
                            }
                            requests.anyRequest().authenticated();
                        }
                )
                .csrf(AbstractHttpConfigurer::disable)
        ;
        http.oauth2ResourceServer(resourceServerConfigurer ->
                resourceServerConfigurer
                        .jwt(jwtConfigurer -> jwtAuthenticationConverter())
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
        );
        return http.build();
    }

    /**
     * 不走过滤器链的放行配置
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                AntPathRequestMatcher.antMatcher("/webjars/**"),
                AntPathRequestMatcher.antMatcher("/doc.html"),
                AntPathRequestMatcher.antMatcher("/swagger-resources/**"),
                AntPathRequestMatcher.antMatcher("/v3/api-docs/**"),
                AntPathRequestMatcher.antMatcher("/swagger-ui/**")
        );
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    /**
     * 提供一个自定义的JWT转换器Bean，用于将JWT令牌转换成 {@link AbstractAuthenticationToken} 实例
     *
     * @return Converter
     * @see JwtAuthenticationProvider#setJwtAuthenticationConverter(Converter) 设置JWT授权转换逻辑
     */
    @Bean
    public Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        // 创建授权转换器，用于从JWT中提取授权信息
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // 设置转换器不添加前缀到授权，通常用于去除默认的"SCOPE_"前缀
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(Strings.EMPTY);
        // 设置JWT的claims中包含授权信息的属性名，默认是"scope"
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(JwtClaimConstants.AUTHORITIES);

        // 创建JWT认证转换器，用于将JWT转换为Spring Security认证令牌
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        // 将自定义的授权转换器设置到JWT认证转换器中
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }


}
