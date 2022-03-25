package com.demain.platform.auth.exception.resource;

import com.demain.platform.core.util.ResponseUtils;
import com.demain.platform.core.util.Result;
import com.demain.platform.core.util.ResultEnum;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TOKEN失效或者校验失败处理器
 * @author: 明天
 * @date: 2022/3/25 10:18
 */
@Component
@Primary
public class OAuthResourceAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ResponseUtils.result(response, Result.error(ResultEnum.TOKEN_INVALID.getCode(), ResultEnum.TOKEN_INVALID.getMsg()));
    }
}
