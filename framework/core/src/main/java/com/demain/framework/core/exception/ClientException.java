package com.demain.framework.core.exception;

import com.demain.framework.core.response.ResponseCode;

import java.io.Serial;

/**
 * 自定义客户端异常
 *
 * @author demain_lee
 * @since 2024/1/22
 */
public class ClientException extends AbstractException {
    
    @Serial
    private static final long serialVersionUID = -1576347536081647705L;
    
    public ClientException(String message) {
        this(ResponseCode.SERVICE_ERROR, message, null);
    }
    
    public ClientException(ResponseCode responseCode) {
        this(responseCode, responseCode.getMessage(), null);
    }
    
    public ClientException(ResponseCode responseCode, String message) {
        this(responseCode, message, null);
    }
    
    public ClientException(ResponseCode responseCode, String message, Throwable throwable) {
        super(responseCode, message, throwable);
    }
}
