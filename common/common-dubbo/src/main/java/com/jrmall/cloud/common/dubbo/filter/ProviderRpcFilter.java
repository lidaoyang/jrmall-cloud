package com.jrmall.cloud.common.dubbo.filter;

import com.jrmall.cloud.common.constant.GlobalConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.MDC;

/**
 * @author: Dao-yang.
 * @date: Created in 2023/5/19 22:13
 */
@Slf4j
@Activate(group = CommonConstants.PROVIDER, order = -30001)
public class ProviderRpcFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = invocation.getAttachment(GlobalConstants.TRACE_ID);
        log.info("进入 Provider RPC Filter traceId:{},methodName:{}", traceId, invocation.getMethodName());
        if (traceId != null) {
            MDC.put(GlobalConstants.TRACE_ID, traceId);
        }
        return invoker.invoke(invocation);
    }
}
