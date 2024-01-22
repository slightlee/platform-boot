package com.demain.framework.core.exception;

import com.demain.framework.core.response.ResponseCode;

/**
 * 自定义远程调用异常
 *
 * @author demain_lee
 * @since 2024/1/22
 */
public class RemoteException extends AbstractException {

    public RemoteException() {
        super(ResponseCode.THIRD_PARTY_SERVICE_ERROR, null, null);
    }

    public RemoteException(ResponseCode responseCode) {
        super(responseCode, responseCode.getMessage(), null);
    }

    public RemoteException(ResponseCode responseCode, String message) {
        super(responseCode, message, null);
    }

    public RemoteException(String code, String message) {
        super(code, message, null);
    }

    public RemoteException(ResponseCode responseCode, String message, Throwable throwable) {
        super(responseCode, message, throwable);
    }
}
