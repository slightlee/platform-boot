package com.demain.framework.security.handler;

import com.demain.framework.security.response.ResponseCode;
import com.demain.framework.security.response.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 自定义拒绝访问处理器
 *
 * @author demain_lee
 * @since 0.0.1
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {
        if (request.getUserPrincipal() instanceof AbstractOAuth2TokenAuthenticationToken) {
            ResponseResult.exceptionResponse(response, ResponseCode.NO_API_PERMISSION.getCode(),
                    ResponseCode.NO_API_PERMISSION.getMessage());
        } else {
            ResponseResult.exceptionResponse(response, accessDeniedException);
        }
    }
}
