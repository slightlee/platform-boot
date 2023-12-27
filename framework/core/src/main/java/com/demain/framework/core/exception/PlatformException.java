package com.demain.framework.core.exception;

import com.demain.framework.core.response.ResponseCode;
import lombok.Data;

/**
 * 自定义处理异常
 */
@Data
public class PlatformException extends RuntimeException {

    private int code;

    private String msg;

    private String errorMsg;

    public PlatformException() {
        this.code = ResponseCode.INTERNAL_SERVER_ERROR.code;
        this.msg = ResponseCode.INTERNAL_SERVER_ERROR.desc;
    }

    public PlatformException(String message) {
        this.code = ResponseCode.INTERNAL_SERVER_ERROR.code;
        this.msg = message;
    }

    public PlatformException(ResponseCode resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getDesc();
    }

    public PlatformException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
