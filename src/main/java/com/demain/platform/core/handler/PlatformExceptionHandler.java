package com.demain.platform.core.handler;

import com.demain.platform.core.exception.PlatformException;
import com.demain.platform.core.util.Result;
import com.demain.platform.core.util.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
public class PlatformExceptionHandler {

    /**
     * 全局异常捕捉处理
     */
    @ExceptionHandler(value = {Exception.class})
    public Result handlerException(Exception exception, HttpServletRequest request) {
        log.error("请求路径uri={},系统内部出现异常:{}", request.getRequestURI(), exception);
        Result result = Result.error(ResultEnum.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage());
        return result;
    }


    /**
     * 自定义异常-PlatformException
     */
    @ExceptionHandler(value = {PlatformException.class})
    public Result handlerCustomException(PlatformException exception, HttpServletRequest request) {
        log.error("请求路径uri={},出现异常:{}", request.getRequestURI(), exception);
        String errorMessage = exception.getMsg();
        Result result = Result.error(exception.getCode(), errorMessage);
        return result;
    }

    /**
     * 自定义validation异常-PlatformException
     *
     * BindException（@Validated @Valid仅对于表单提交有效，对于以json格式提交将会失效）
     */
    @ExceptionHandler(value = {BindException.class})
    public Result handlerValidationException(BindException exception, HttpServletRequest request) {
        log.error("请求路径uri={},出现异常:{}", request.getRequestURI(), exception);
        System.out.println(exception);
        String errorMessage = exception.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));
        Result result = Result.error(ResultEnum.PARAMETER_VALIDATION_FAIL, errorMessage);
        return result;
    }

    /**
     *  MethodArgumentNotValidException（@Validated @Valid 前端提交的方式为json格式有效，出现异常时会被该异常类处理）
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Result handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        log.error("请求路径uri={},出现异常:{}", request.getRequestURI(), exception);
        System.out.println(exception);
        BindingResult bindingResult = exception.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        String messages = objectError.getDefaultMessage();
        Result result = Result.error(ResultEnum.PARAMETER_VALIDATION_FAIL.getCode(), messages);
        return result;
    }

    /**
     * OAuth2Exception 异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = OAuth2Exception.class)
    public Result handleOauth2(OAuth2Exception e) {
        ResultEnum resultEnum = ResultEnum.UNAUTHORIZED;
        if(e instanceof UnsupportedGrantTypeException){
            resultEnum = ResultEnum.UNSUPPORTED_GRANT_TYPE;
            //用户名或密码异常
        }else if(e instanceof InvalidGrantException){
            resultEnum = ResultEnum.ACCOUNT_PASSWORD_IS_ERROR;
            log.info(e.getLocalizedMessage());
            if("User account is locked".equals(e.getLocalizedMessage())){
                resultEnum = ResultEnum.ACCOUNT_DISABLE;
            }
        }
        return Result.error(resultEnum.getCode(),resultEnum.getMsg());
    }

}
