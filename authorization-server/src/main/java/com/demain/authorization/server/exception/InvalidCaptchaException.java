package com.demain.authorization.server.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常
 *
 * @author demain_lee
 * @since 2024/09/27
 */
public class InvalidCaptchaException extends AuthenticationException {
    
    public InvalidCaptchaException(String msg) {
        super(msg);
    }
    
}