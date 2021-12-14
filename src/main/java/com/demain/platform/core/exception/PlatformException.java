package com.demain.platform.core.exception;

import com.demain.platform.core.util.ResultEnum;
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
        this.code = ResultEnum.INTERNAL_SERVER_ERROR.code;
        this.msg = ResultEnum.INTERNAL_SERVER_ERROR.msg;
    }

    public PlatformException(String message) {
        this.code = ResultEnum.INTERNAL_SERVER_ERROR.code;
        this.msg = message;
    }

    public PlatformException(ResultEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMsg();
    }

    public PlatformException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
