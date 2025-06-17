package com.jrmall.pilates.common.exception;

import com.jrmall.pilates.common.result.IResultCode;
import lombok.Getter;

/**
 * 自定义提供者服务异常
 */
@Getter
public class ProviderException extends RuntimeException {

    public IResultCode resultCode;

    public ProviderException(IResultCode errorCode) {
        super(errorCode.getMsg());
        this.resultCode = errorCode;
    }

    public ProviderException(IResultCode errorCode,String message) {
        super(message);
        this.resultCode = errorCode;
    }

    public ProviderException(String message){
        super(message);
    }

    public ProviderException(String message, Throwable cause){
        super(message, cause);
    }

    public ProviderException(Throwable cause){
        super(cause);
    }


}
