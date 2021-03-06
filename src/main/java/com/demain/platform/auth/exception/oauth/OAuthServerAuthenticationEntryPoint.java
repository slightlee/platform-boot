package com.demain.platform.auth.exception.oauth;

import com.demain.platform.core.util.ResponseUtils;
import com.demain.platform.core.util.Result;
import com.demain.platform.core.util.ResultEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用于处理客户端认证出错，包括客户端id、密码错误
 * @author: 明天
 * @date: 2022/3/25 10:20
 */
@Component
public class OAuthServerAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 认证失败处理器会调用这个方法返回提示信息
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ResponseUtils.result(response, Result.error(ResultEnum.CLIENT_AUTHENTICATION_FAILED.getCode(), ResultEnum.CLIENT_AUTHENTICATION_FAILED.getMsg()));
    }
}