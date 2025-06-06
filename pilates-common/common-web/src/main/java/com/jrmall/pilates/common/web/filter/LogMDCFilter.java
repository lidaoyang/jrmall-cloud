package com.jrmall.pilates.common.web.filter;

import cn.hutool.core.util.IdUtil;
import com.jrmall.pilates.common.constant.GlobalConstants;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.io.IOException;

/**
 * @author: Dao-yang.
 * @date: Created in 2023/5/16 15:06
 */
@Slf4j
public class LogMDCFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String traceId = getRequestId(httpServletRequest);
        MDC.put(GlobalConstants.TRACE_ID, traceId);

        String clientIp = getClientIp(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();
        log.info("{} {}:{} 请求开始", clientIp, httpServletRequest.getMethod(), requestURI);
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            log.info("{} {}:{} 请求结束", clientIp, httpServletRequest.getMethod(), requestURI);
            MDC.remove(GlobalConstants.TRACE_ID);
        }
    }

    public static String getRequestId(HttpServletRequest request) {
        String traceId;
        String parameterRequestId = request.getParameter(GlobalConstants.TRACE_ID);
        String headerRequestId = request.getHeader(GlobalConstants.TRACE_ID);
        // 根据请求参数或请求头判断是否有“request-id”，有则使用，无则创建
        if (parameterRequestId == null && headerRequestId == null) {
            traceId = IdUtil.simpleUUID();
        } else {
            traceId = parameterRequestId != null ? parameterRequestId : headerRequestId;
        }
        return traceId;
    }

    /**
     * 获取客户端ip
     *
     * @param request 参数
     * @return String
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (checkIsIp(ip)) {
            return ip;
        }

        ip = request.getHeader("X-Real-IP");
        if (checkIsIp(ip)) {
            return ip;
        }

        ip = request.getRemoteAddr();
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
}
