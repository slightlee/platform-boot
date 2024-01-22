package com.demain.framework.core.exception;

import com.demain.framework.core.response.ResponseCode;
import lombok.Getter;

import java.io.Serial;
import java.util.Optional;

/**
 * 自定义抽象异常
 *
 * @author demain_lee
 * @since 2024/1/22
 */
@Getter
public class AbstractException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -8215256748995260662L;

    private final String code;
    private final String message;

    public AbstractException(ResponseCode responseCode, String message, Throwable throwable) {
        super(message, throwable);
        this.code = responseCode.getCode();
        this.message = Optional.ofNullable(message).orElse(responseCode.getMessage());
    }

    public AbstractException(String code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
        this.message = message;
    }
}
