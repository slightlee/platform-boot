package com.demain.authorization.server.handler;

import com.demain.authorization.server.response.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * 自定义登录失败处理器
 *
 * @author demain_lee
 * @since 2025/01/16
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException {
        ResponseResult.exceptionResponse(response, exception);
    }
}
