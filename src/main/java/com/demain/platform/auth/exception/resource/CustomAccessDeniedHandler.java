package com.demain.platform.auth.exception.resource;

import com.demain.platform.core.util.ResponseUtils;
import com.demain.platform.core.util.Result;
import com.demain.platform.core.util.ResultEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证后的用户访问受保护的资源时，权限不够时进入此处理器
 * @author: 明天
 * @date: 2022/3/25 10:15
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ResponseUtils.result(response,Result.error(ResultEnum.NO_PERMISSION.getCode(),ResultEnum.NO_PERMISSION.getMsg()));
    }
}
