package com.demain.framework.security.handler;

import com.demain.framework.security.response.ResponseCode;
import com.demain.framework.security.response.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.Map;

/**
 * 自定义登录失败处理器
 *
 * @author demain_lee
 * @since 2025/01/16
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    
    // 错误码与自定义响应的映射
    private static final Map<String, ResponseCode> ERROR_CODE_MAPPING = Map.of(
            OAuth2ErrorCodes.INVALID_CLIENT, ResponseCode.INVALID_CLIENT,
            OAuth2ErrorCodes.UNSUPPORTED_GRANT_TYPE, ResponseCode.UNSUPPORTED_GRANT_TYPE);
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException {
        if (exception instanceof OAuth2AuthenticationException o) {
            String errorCode = o.getError().getErrorCode();
            ResponseCode responseCode = ERROR_CODE_MAPPING.get(errorCode);
            if (responseCode != null) {
                // 根据映射的响应码返回
                ResponseResult.exceptionResponse(response, responseCode.getCode(), responseCode.getMessage());
            } else {
                // 处理未映射的错误
                ResponseResult.exceptionResponse(response, exception);
            }
        } else {
            ResponseResult.exceptionResponse(response, exception);
        }
    }
}