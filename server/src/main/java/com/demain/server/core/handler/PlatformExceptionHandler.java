package com.demain.server.core.handler;

import com.demain.framework.core.exception.PlatformException;
import com.demain.framework.core.response.ResponseCode;
import com.demain.framework.core.response.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class PlatformExceptionHandler {

    /**
     * 全局异常捕捉处理
     */
    @ExceptionHandler(value = {Exception.class})
    public Result handlerException(Exception exception, HttpServletRequest request) {
        log.error("请求路径uri={},系统内部出现异常:{}", request.getRequestURI(), exception);
        Result result = Result.fail(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), exception.getLocalizedMessage());
        return result;
    }


    /**
     * 自定义异常-PlatformException
     */
    @ExceptionHandler(value = {PlatformException.class})
    public Result handlerCustomException(PlatformException exception, HttpServletRequest request) {
        log.error("请求路径uri={},出现异常:{}", request.getRequestURI(), exception);
        String errorMessage = exception.getMsg();
        Result result = Result.fail(exception.getCode(), errorMessage);
        return result;
    }

}
