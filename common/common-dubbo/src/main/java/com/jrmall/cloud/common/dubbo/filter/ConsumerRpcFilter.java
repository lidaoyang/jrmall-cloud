package com.jrmall.cloud.common.dubbo.filter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.jrmall.cloud.common.constant.GlobalConstants;
import com.jrmall.cloud.common.constant.JwtClaimConstants;
import com.jrmall.cloud.common.dubbo.constant.RpcAttKeyConstant;
import com.jrmall.cloud.common.dubbo.model.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author: Dao-yang.
 * @date: Created in 2023/5/19 22:13
 */
@Slf4j
@Activate(group = CommonConstants.CONSUMER, order = -10001)
public class ConsumerRpcFilter implements Filter {


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = MDC.get(GlobalConstants.TRACE_ID);
        log.info("进入 Consumer RPC Filter traceId:{},methodName:{}", traceId, invocation.getMethodName());

        //  设置登录用户信息
        LoginUser loginUser = getLoginUser();
        RpcContext.getClientAttachment().setAttachment(RpcAttKeyConstant.LOGIN_USER, loginUser);

        if (traceId != null) {
            RpcContext.getClientAttachment().setAttachment(GlobalConstants.TRACE_ID, traceId);
            invocation.setAttachment(GlobalConstants.TRACE_ID, traceId);
            MDC.put(GlobalConstants.TRACE_ID, traceId);
        }
        return invoker.invoke(invocation);
    }

    private LoginUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(authentication.getName());
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof String)) {
            String principalStr = JSON.toJSONString(principal);
            JSONObject principalJ = JSON.parseObject(principalStr);
            JSONObject claimsJ = principalJ.getJSONObject("claims");
            loginUser.setJti(claimsJ.getString("jti"));
            loginUser.setUserId(claimsJ.getLong(JwtClaimConstants.USER_ID));
            loginUser.setDeptId(claimsJ.getLong(JwtClaimConstants.DEPT_ID));
            loginUser.setDataScope(claimsJ.getInteger(JwtClaimConstants.DATA_SCOPE));
            loginUser.setExp(claimsJ.getInstant("exp"));
            claimsJ.getJSONArray(JwtClaimConstants.AUTHORITIES).forEach(role -> loginUser.getRoles().add(role.toString()));
        }
        return loginUser;
    }
}
