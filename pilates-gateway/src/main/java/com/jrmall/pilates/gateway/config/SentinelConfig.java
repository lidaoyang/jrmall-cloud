package com.jrmall.pilates.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.jrmall.pilates.common.result.ResultCode;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * 自定义网关流控异常
 *
 * @author haoxr
 * @since 2022/7/24
 */
@Configuration
public class SentinelConfig {

    @PostConstruct
    private void initBlockHandler() {
        GatewayCallbackManager.setBlockHandler(
                (exchange, ex) -> {
                    String msg = "触发服务保护！";
                    if (ex instanceof FlowException) {
                        msg = "请求过于频繁，被限流了！";
                    } else if (ex instanceof DegradeException) {
                        msg = "请求过于频繁，被降级了！";
                    } else if (ex instanceof ParamFlowException) {
                        msg = "热点数据请求过于频繁！";
                    } else if (ex instanceof SystemBlockException) {
                        msg = "请求过于频繁，触发系统限制！";
                    } else if (ex instanceof AuthorityException) {
                        msg = "授权规则不通过！";
                    }
                    return ServerResponse
                            .status(HttpStatus.TOO_MANY_REQUESTS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(ResultCode.FLOW_LIMITING.toString()));
                }
        );
    }
}
