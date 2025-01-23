package com.demain.authorization.server.handler;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;

/**
 * 授权类型处理器
 *
 * @author demain_lee
 * @since 0.0.1
 */
@FunctionalInterface
public interface GrantTypeHandler {
    
    void handle(JwtEncodingContext context, JwtClaimsSet.Builder claims);
}