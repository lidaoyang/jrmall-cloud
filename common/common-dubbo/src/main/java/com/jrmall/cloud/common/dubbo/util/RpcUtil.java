package com.jrmall.cloud.common.dubbo.util;

import com.jrmall.cloud.common.constant.SystemConstants;
import com.jrmall.cloud.common.dubbo.constant.RpcAttKeyConstant;
import com.jrmall.cloud.common.dubbo.model.LoginUser;
import com.jrmall.cloud.common.exception.ProviderException;
import com.jrmall.cloud.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.RpcContext;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

/**
 * @author: Dao-yang.
 * @date: Created in 2023/7/13 15:15
 */
@Slf4j
public class RpcUtil {

    public static LoginUser getLoginUserException() {
        LoginUser LoginUser = getLoginUser();
        if (LoginUser == null || LoginUser.getJti() == null) {
            throw new ProviderException(ResultCode.USER_LOGIN_ERROR);
        }
        return LoginUser;
    }

    public static LoginUser getLoginUser() {
        Object objectAttachment = getAttachmentObject(RpcAttKeyConstant.LOGIN_USER);
        if (objectAttachment == null) {
            log.warn("用户未登录");
            return null;
        }
        return (LoginUser) objectAttachment;
    }

    public static Long getUserId() {
        return Objects.requireNonNull(getLoginUser()).getUserId();
    }

    public static Long getDeptId() {
        return Objects.requireNonNull(getLoginUser()).getDeptId();
    }

    public static String getUsername() {
        return Objects.requireNonNull(getLoginUser()).getUsername();
    }

    public static String getJti() {
        return Objects.requireNonNull(getLoginUser()).getJti();
    }

    public static Instant getExp() {
        return Objects.requireNonNull(getLoginUser()).getExp();
    }

    public static Integer getDataScope() {
        return Objects.requireNonNull(getLoginUser()).getDataScope();
    }

    public static Set<String> getRoles() {
        return Objects.requireNonNull(getLoginUser()).getRoles();
    }

    public static boolean isRoot() {
        return getRoles().contains(SystemConstants.ROOT_ROLE_CODE);
    }

    public static String getServerAttachment(String attKey) {
        return RpcContext.getServerAttachment().getAttachment(attKey);
    }

    public static String getClientAttachment(String attKey) {
        return RpcContext.getClientAttachment().getAttachment(attKey);
    }

    public static Object getServerAttachmentObject(String attKey) {
        return RpcContext.getServerAttachment().getObjectAttachment(attKey);
    }

    public static Object getClientAttachmentObject(String attKey) {
        return RpcContext.getClientAttachment().getObjectAttachment(attKey);
    }

    public static void setServerAttachment(String attKey, String value) {
        RpcContext.getServerAttachment().setAttachment(attKey, value);
    }

    public static void setClientAttachment(String attKey, String value) {
        RpcContext.getClientAttachment().setAttachment(attKey, value);
    }

    public static void setServerAttachment(String attKey, Object value) {
        RpcContext.getServerAttachment().setAttachment(attKey, value);
    }

    public static void setClientAttachment(String attKey, Object value) {
        RpcContext.getClientAttachment().setAttachment(attKey, value);
    }

    public static void setAttachment(String attKey, String value) {
        RpcContext.getClientAttachment().setAttachment(attKey, value);
        RpcContext.getServerAttachment().setAttachment(attKey, value);
    }

    public static String getAttachment(String attKey) {
        String attachment = RpcContext.getServerAttachment().getAttachment(attKey);
        if (StringUtils.isNotBlank(attachment)) {
            return attachment;
        }
        return RpcContext.getClientAttachment().getAttachment(attKey);
    }

    public static void setAttachment(String attKey, Object value) {
        RpcContext.getClientAttachment().setAttachment(attKey, value);
        RpcContext.getServerAttachment().setAttachment(attKey, value);
    }

    public static Object getAttachmentObject(String attKey) {
        Object attachment = RpcContext.getServerAttachment().getObjectAttachment(attKey);
        if (!Objects.isNull(attachment)) {
            return attachment;
        }
        return RpcContext.getClientAttachment().getObjectAttachment(attKey);
    }

}
