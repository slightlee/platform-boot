package com.demain.authorization.server.authentication.oidc;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationConverter;

public class CustomOidcUserInfoAuthenticationConverter implements AuthenticationConverter {
    
    private final CustomOidcUserInfoService customOidcUserInfoService;
    
    public CustomOidcUserInfoAuthenticationConverter(
                                                     CustomOidcUserInfoService customOidcUserInfoService) {
        this.customOidcUserInfoService = customOidcUserInfoService;
    }
    
    @Override
    public Authentication convert(HttpServletRequest request) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // 获取用户信息
        CustomOidcUserInfo customOidcUserInfo =
                customOidcUserInfoService.loadUserInfo(authentication.getName());
        // 返回自定义的OidcUserInfoAuthenticationToken
        return new OidcUserInfoAuthenticationToken(authentication, customOidcUserInfo);
    }
    
}
