package com.demain.framework.security.handler;

import com.demain.framework.security.constant.SecurityConstants;
import com.demain.framework.security.response.ResponseCode;
import com.demain.framework.security.response.ResponseResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.io.IOException;

/**
 * 自定义认证入口点
 *
 * @author demain_lee
 * @since 2025/01/10
 */
public class CustomeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        String accept = request.getHeader("accept");
        if (authException instanceof InsufficientAuthenticationException) {
            if (accept.contains(MediaType.TEXT_HTML_VALUE)) {
                // 如果是html请求类型，则返回登录页面
                LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint =
                        new LoginUrlAuthenticationEntryPoint(SecurityConstants.LOGIN_URL);
                loginUrlAuthenticationEntryPoint.commence(request, response, authException);
            } else {
                // 如果是api请求类型，则返回json数据
                ResponseResult.exceptionResponse(response, ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
                        ResponseCode.UNAUTHORIZED_ACCESS.getMessage());
            }
        } else if (authException instanceof InvalidBearerTokenException) {
            ResponseResult.exceptionResponse(response, ResponseCode.UNAUTHORIZED_ACCESS.getCode(),
                    ResponseCode.AUTHORIZATION_EXPIRED.getMessage());
        } else if (authException instanceof UsernameNotFoundException o) {
            ResponseResult.exceptionResponse(response, ResponseCode.USER_NOT_FOUND.getCode(), o.getMessage());
        } else {
            ResponseResult.exceptionResponse(response, authException);
        }
    }
}
