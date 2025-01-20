package com.demain.framework.security.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * 返回对象
 *
 * @author demain_lee
 * @since 0.0.1
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ResponseResult<T> implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 5437561015042871187L;
    
    private static final Logger logger = LoggerFactory.getLogger(ResponseResult.class);
    
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
    
    public ResponseResult(String code, String message, T data, long timestamp) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }
    
    public static <T> ResponseResult<T> build(T data, ResponseCode responseCode) {
        return new ResponseResult<T>(responseCode.getCode(), responseCode.getMessage(), data,
                System.currentTimeMillis());
    }
    
    public static <T> ResponseResult<T> build(T data, ResponseCode responseCode, String message) {
        return new ResponseResult<T>(responseCode.getCode(), message, data,
                System.currentTimeMillis());
    }
    
    public static <T> ResponseResult<T> build(T data, String errorCode, String message) {
        return new ResponseResult<T>(errorCode, message, data,
                System.currentTimeMillis());
    }
    
    /**
     * 构建成功返回对象
     *
     * @param <T> 返回数据类型
     * @return 返回对象
     */
    public static <T> ResponseResult<T> success() {
        return build(null, ResponseCode.SUCCESS);
    }
    
    /**
     * 构建成功返回对象
     *
     * @param data 返回数据
     * @param <T> 返回数据类型
     * @return 返回对象
     */
    public static <T> ResponseResult<T> success(T data) {
        return build(data, ResponseCode.SUCCESS);
    }
    
    /**
     * 构建失败返回对象
     *
     * @param <T> 返回数据类型
     * @return 返回对象
     */
    public static <T> ResponseResult<T> fail() {
        logger.debug("请求失败错误信息如下：code={}, message={}", ResponseCode.SERVICE_ERROR.getCode(),
                ResponseCode.SERVICE_ERROR.getMessage());
        return build(null, ResponseCode.SERVICE_ERROR);
    }
    
    /**
     * 构建失败返回对象
     *
     * @param message 错误信息
     * @param <T> 返回数据类型
     * @return 返回对象
     */
    public static <T> ResponseResult<T> fail(String message) {
        logger.debug("请求失败错误信息如下：code={}, message={}", ResponseCode.SERVICE_ERROR.getCode(), message);
        return build(null, ResponseCode.SERVICE_ERROR, message);
    }
    
    /**
     * 构建失败返回对象
     *
     * @param errorCode 错误码
     * @param message 错误信息
     * @param <T> 返回数据类型
     * @return 返回对象
     */
    public static <T> ResponseResult<T> fail(String errorCode, String message) {
        logger.debug("请求失败错误信息如下：code={}, message={}", errorCode, message);
        return build(null, errorCode, message);
    }
    
    public static void exceptionResponse(HttpServletResponse response,
            Exception e) throws IOException {
        String errorCode = "";
        String message = "";
        if (e instanceof OAuth2AuthenticationException o) {
            errorCode = o.getError().getErrorCode();
            message = o.getError().getDescription();
        } else {
            message = e.getMessage();
        }
        exceptionResponse(response, errorCode, message);
    }
    
    public static void exceptionResponse(HttpServletResponse response,
            String errorCode, String message) throws AccessDeniedException, AuthenticationException, IOException {
        ResponseResult<Object> responseResult =
                ObjectUtils.isEmpty(errorCode) ? ResponseResult.fail(message) : ResponseResult.fail(errorCode, message);
        Gson gson = new Gson();
        String jsonResult = gson.toJson(responseResult);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().print(jsonResult);
    }
    
}
