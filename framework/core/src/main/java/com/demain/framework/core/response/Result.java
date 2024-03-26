package com.demain.framework.core.response;

import com.demain.framework.core.exception.AbstractException;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Optional;

/**
 * 返回对象
 *
 * @author demain_lee
 * @since 0.0.1
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Result<T> implements Serializable {
    
    private static final Logger logger = LoggerFactory.getLogger(Result.class);
    
    /**
     * 状态码
     */
    private String code;
    
    /**
     * 描述信息
     */
    private String message;
    
    /**
     * 返回数据
     */
    private T data;
    
    /**
     * 时间戳
     */
    private long timestamp;
    
    private Result(String code) {
        this.code = code;
    }
    
    private Result(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public Result(String code, String message, long timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }
    
    private Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    public Result(String code, String message, T data, long timestamp) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }
    
    public static <T> Result<T> success() {
        return new Result<T>(ResponseCode.OK.getCode());
    }
    
    public static <T> Result<T> success(String message) {
        return new Result<T>(ResponseCode.OK.getCode(), message);
    }
    
    public static <T> Result<T> success(ResponseCode resultEnum) {
        return new Result<T>(resultEnum.getCode(), resultEnum.getMessage());
    }
    
    public static <T> Result<T> data(T data) {
        return new Result<T>(ResponseCode.OK.getCode(), ResponseCode.OK.getMessage(), data, System.currentTimeMillis());
    }
    
    public static <T> Result<T> fail() {
        logger.debug("请求失败错误信息如下：code={}", ResponseCode.SERVICE_ERROR.getCode());
        return new Result<T>(ResponseCode.SERVICE_ERROR.getCode());
    }
    
    public static <T> Result<T> fail(String message) {
        logger.debug("请求失败错误信息如下：code={}, message={}", ResponseCode.SERVICE_ERROR.getCode(), message);
        return new Result<T>(ResponseCode.SERVICE_ERROR.getCode(), message);
    }
    
    public static <T> Result<T> fail(String code, String message) {
        logger.debug("请求失败错误信息如下：code={}, message={}", code, message);
        return new Result<T>(code, message, System.currentTimeMillis());
    }
    
    public static <T> Result<T> fail(ResponseCode resultEnum) {
        logger.debug("请求失败错误信息如下：code={}, message={}", resultEnum.getCode(), resultEnum.getMessage());
        return new Result<T>(resultEnum.getCode(), resultEnum.getMessage());
    }
    
    public static Result<Void> fail(AbstractException abstractException) {
        String errorCode = Optional.ofNullable(abstractException.getCode())
                .orElse(ResponseCode.SERVICE_ERROR.getCode());
        String errorMessage = Optional.ofNullable(abstractException.getMessage())
                .orElse(ResponseCode.SERVICE_ERROR.getMessage());
        return fail(errorCode, errorMessage);
    }
    
}
