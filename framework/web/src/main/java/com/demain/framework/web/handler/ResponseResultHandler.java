package com.demain.framework.web.handler;

import com.demain.framework.core.response.Result;
import com.demain.framework.web.annotation.ResponseResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.Serializable;
import java.util.Objects;

/**
 * 统一处理响应体，用Result.data静态方法包装，
 * 在API接口使用时就可以直接返回原始类型
 */
@RestControllerAdvice
public class ResponseResultHandler<T extends Serializable> implements ResponseBodyAdvice<T> {
    
    /**
     * 使用统一返回体的标识
     */
    private static final String RESPONSE_RESULT_ANNOTATION = "RESPONSE-RESULT-ANNOTATION";
    
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(sra).getRequest();
        ResponseResult responseResult = (ResponseResult) request.getAttribute(RESPONSE_RESULT_ANNOTATION);
        // 判断返回体是否需要处理
        return responseResult != null;
    }
    
    @Override
    public T beforeBodyWrite(T body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
                             ServerHttpResponse serverHttpResponse) {
        // 异常响应体则直接返回code+message的消息体
        if (body instanceof Result) {
            return body;
        }
        if (body instanceof String) {
            // 当响应体是String类型时，使用ObjectMapper转换，因为Spring默认使用StringHttpMessageConverter处理字符串，不会将字符串识别为JSON
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return (T) objectMapper.writeValueAsString(Result.data(body));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        // 正常响应体则返回Result包装的code+message+data的消息体
        return (T) Result.data(body);
    }
}
