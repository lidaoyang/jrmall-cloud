package com.jrmall.pilates.common.exception;

import com.jrmall.pilates.common.result.IResultCode;
import lombok.Getter;

/**
 * 自定义提供者拒绝访问服务异常
 */
public class ProviderAccessDeniedException extends ProviderException {


    public ProviderAccessDeniedException(Throwable cause) {
        super(cause);
    }

    public ProviderAccessDeniedException(IResultCode errorCode) {
        super(errorCode);
    }

    public ProviderAccessDeniedException(String message) {
        super(message);
    }

    public ProviderAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
