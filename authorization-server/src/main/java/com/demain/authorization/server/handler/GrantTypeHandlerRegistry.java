package com.demain.authorization.server.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 授权类型处理器注册表
 *
 * @author demain_lee
 * @since 0.0.1
 */
@Component
public class GrantTypeHandlerRegistry {
    
    private final Map<String, GrantTypeHandler> handlers = new HashMap<>();
    
    @Autowired
    public GrantTypeHandlerRegistry(UserDetailsService userDetailsService) {
        // 注册密码模式处理器
        handlers.put(AuthorizationGrantType.PASSWORD.getValue(), (context, claims) -> {
            UserDetails userDetails = userDetailsService.loadUserByUsername(context.getPrincipal().getName());
            claims.claim("username", userDetails.getUsername());
            claims.claim("grant_type", AuthorizationGrantType.PASSWORD.getValue());
            // 合并用户权限到scope
            claims.claims(claimsConsumer -> {
                claimsConsumer.merge("scope", userDetails.getAuthorities(), (scope, authorities) -> {
                    Set<String> scopeSet = (Set<String>) scope;
                    Set<String> cloneSet = scopeSet.stream().map(String::new).collect(Collectors.toSet());
                    Collection<SimpleGrantedAuthority> simpleGrantedAuthorities =
                            (Collection<SimpleGrantedAuthority>) authorities;
                    simpleGrantedAuthorities.forEach(simpleGrantedAuthority -> {
                        if (!cloneSet.contains(simpleGrantedAuthority.getAuthority())) {
                            cloneSet.add(simpleGrantedAuthority.getAuthority());
                        }
                    });
                    return cloneSet;
                });
            });
        });
        
        // 注册客户端模式处理器
        handlers.put(AuthorizationGrantType.CLIENT_CREDENTIALS.getValue(), (context, claims) -> {
            claims.claim("client_id", context.getRegisteredClient().getClientId());
            claims.claim("scope", context.getAuthorizedScopes());
            claims.claim("grant_type", AuthorizationGrantType.CLIENT_CREDENTIALS.getValue());
        });
    }
    
    public GrantTypeHandler getHandler(String grantType) {
        return handlers.get(grantType);
    }
}