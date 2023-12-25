package com.demain.framework.core.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;

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
    private Integer code;

    /**
     * 描述信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    private Result(Integer code) {
        this.code = code;
    }

    private Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public boolean isSuccess() {
        return Objects.equals(this.code, ResponseCode.SUCCESS.getCode());
    }

    public static <T> Result<T> success() {
        return new Result<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> Result<T> success(String message) {
        return new Result<T>(ResponseCode.SUCCESS.getCode(), message);
    }


    public static <T> Result<T> success(ResponseCode resultEnum) {
        return new Result<T>(resultEnum.getCode(), resultEnum.getDesc());
    }


    public static <T> Result<T> data(T data) {
        return new Result<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc(), data);
    }


    public static <T> Result<T> fail() {
        logger.debug("请求失败错误信息如下：code={}", ResponseCode.INTERNAL_SERVER_ERROR.getCode());
        return new Result<T>(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
    }

    public static <T> Result<T> fail(String message) {
        logger.debug("请求失败错误信息如下：code={}, message={}", ResponseCode.INTERNAL_SERVER_ERROR.getCode(), message);
        return new Result<T>(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), message);
    }

    public static <T> Result<T> fail(int code, String message) {
        logger.debug("请求失败错误信息如下：code={}, message={}", code, message);
        return new Result<T>(code, message);
    }

    public static <T> Result<T> fail(ResponseCode resultEnum) {
        logger.debug("请求失败错误信息如下：code={}, message={}", resultEnum.getCode(), resultEnum.getDesc());
        return new Result<T>(resultEnum.getCode(), resultEnum.getDesc());
    }

}
