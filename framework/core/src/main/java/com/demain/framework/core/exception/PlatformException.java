package com.demain.framework.core.exception;

import com.demain.framework.core.response.ResponseCode;

import java.io.Serial;

/**
 * 自定义内部服务异常
 *
 * @author demain_lee
 * @since 2024/1/22
 */
public class PlatformException extends AbstractException {
    
    @Serial
    private static final long serialVersionUID = -1060827041982349610L;
    
    public PlatformException() {
        this(ResponseCode.SERVICE_ERROR, null, null);
    }
    
    public PlatformException(String message) {
        this(ResponseCode.SERVICE_ERROR, message, null);
    }
    
    public PlatformException(ResponseCode responseCode) {
        this(responseCode, responseCode.getMessage(), null);
    }
    
    public PlatformException(ResponseCode responseCode, String message) {
        this(responseCode, message, null);
    }
    
    public PlatformException(ResponseCode responseCode, String message, Throwable throwable) {
        super(responseCode, message, throwable);
    }
    
}
