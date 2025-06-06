package com.jrmall.pilates.gateway.filter;

import com.jrmall.pilates.common.constant.GlobalConstants;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.jboss.logging.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author: Dao-yang.
 * @date: Created in 2023/5/16 15:06
 */
@Slf4j
@Component
public class LogMDCFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String traceId = getTraceId(request);
        MDC.put(GlobalConstants.TRACE_ID, traceId);

        String url = request.getURI().getPath();
        String clientIp = getClientIp(request);
        log.info("{} {}:{} 请求开始", clientIp, request.getMethod(), url);
        // 对请求对象request进行增强
        ServerHttpRequest req = request.mutate().headers(headers -> {
            headers.add(GlobalConstants.TRACE_ID, traceId);
        }).build();
        // 设置增强的request到exchange对象中
        exchange.mutate().request(req);
        try {
            return chain.filter(exchange);
        } finally {
            log.info("{} {}:{} 请求结束", clientIp, request.getMethod(), url);
            MDC.remove(GlobalConstants.TRACE_ID);
        }
    }

    private static String getTraceId(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String headerTraceId = headers.getFirst(GlobalConstants.TRACE_ID);
        String paramTraceId = request.getQueryParams().getFirst(GlobalConstants.TRACE_ID);
        return headerTraceId != null ? headerTraceId : (paramTraceId != null ? paramTraceId : UUID.randomUUID().toString().replace("-", ""));
    }

    /**
     * 获取客户端ip
     *
     * @param request 参数
     * @return String
     */
    public static String getClientIp(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("X-Forwarded-For");
        if (checkIsIp(ip)) {
            return ip;
        }

        ip = headers.getFirst("X-Real-IP");
        if (checkIsIp(ip)) {
            return ip;
        }

        ip = request.getRemoteAddress().getHostString();
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            // 本地 localhost访问 ipv6
            ip = "127.0.0.1";
        }
        if (checkIsIp(ip)) {
            return ip;
        }

        return "";
    }

    /**
     * 检测是否为ip
     *
     * @param ip 参数
     * @return String
     */
    public static boolean checkIsIp(String ip) {
        if (StringUtils.isBlank(ip)) {
            return false;
        }

        if ("unKnown".equals(ip)) {
            return false;
        }

        if ("unknown".equals(ip)) {
            return false;
        }

        return ip.split("\\.").length == 4;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
