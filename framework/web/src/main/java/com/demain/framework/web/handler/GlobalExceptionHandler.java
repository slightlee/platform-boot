package com.demain.framework.web.handler;

import cn.hutool.core.util.StrUtil;
import com.demain.framework.core.exception.AbstractException;
import com.demain.framework.core.response.ResponseCode;
import com.demain.framework.core.response.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义全局异常处理
 *
 * @author demain_lee
 * @since 2024/1/3
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 自定义异常
     */
    @ExceptionHandler(value = {AbstractException.class})
    public Result handleAbstractException(AbstractException exception, HttpServletRequest request) {
        String requestURL = getUrl(request);
        log.error("[{}] {} [ex] {}", request.getMethod(), requestURL, exception.toString());
        if (exception.getCause() != null) {
            log.error("[{}] {} [ex] {}", request.getMethod(), requestURL, exception, exception.getCause());
        }
        return Result.fail(exception);
    }


    /**
     * 兜底处理
     */
    @ExceptionHandler(value = {Throwable.class})
    public Result handlerException(Throwable throwable, HttpServletRequest request) {
        log.error("[{}] {} [ex] {}", request.getMethod(), getUrl(request), throwable.toString());
        return Result.fail(ResponseCode.SERVICE_ERROR.getCode(), "系统异常，请联系管理员!");
    }

    private String getUrl(HttpServletRequest request) {
        if (StrUtil.isEmpty(request.getQueryString())) {
            return request.getRequestURL().toString();
        }
        return request.getRequestURL().toString() + "?" + request.getQueryString();
    }
}
