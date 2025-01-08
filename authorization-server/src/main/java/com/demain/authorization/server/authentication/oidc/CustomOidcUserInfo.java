package com.demain.authorization.server.authentication.oidc;

import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.util.Assert;

import java.io.Serial;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 自定义oidc用户信息
 * 
 * @author demain_lee
 * @since 2025/01/05
 */
public class CustomOidcUserInfo extends OidcUserInfo {
    
    @Serial
    private static final long serialVersionUID = 620L;
    
    private final Map<String, Object> claims;
    
    public CustomOidcUserInfo(Map<String, Object> claims) {
        super(claims);
        Assert.notEmpty(claims, "claims cannot be empty");
        this.claims = Collections.unmodifiableMap(new LinkedHashMap<>(claims));
    }
    
    public Map<String, Object> getClaims() {
        return this.claims;
    }
    
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && this.getClass() == obj.getClass()) {
            CustomOidcUserInfo that = (CustomOidcUserInfo) obj;
            return this.getClaims().equals(that.getClaims());
        } else {
            return false;
        }
    }
    
    public int hashCode() {
        return this.getClaims().hashCode();
    }
    
    public static CustomOidcUserInfo.Builder cusBuilder() {
        return new CustomOidcUserInfo.Builder();
    }
    
    public static final class Builder {
        
        private final Map<String, Object> claims = new LinkedHashMap<>();
        
        private Builder() {
        }
        
        public CustomOidcUserInfo.Builder claim(String name, Object value) {
            this.claims.put(name, value);
            return this;
        }
        
        public CustomOidcUserInfo.Builder claims(Consumer<Map<String, Object>> claimsConsumer) {
            claimsConsumer.accept(this.claims);
            return this;
        }
        
        public CustomOidcUserInfo.Builder userName(String username) {
            return this.claim("username", username);
        }
        
        public CustomOidcUserInfo.Builder name(String name) {
            return this.claim("name", name);
        }
        
        public CustomOidcUserInfo.Builder status(Integer status) {
            return this.claim("status", status);
        }
        
        public CustomOidcUserInfo.Builder phoneNumber(String phoneNumber) {
            return this.claim("phone_number", phoneNumber);
        }
        
        public CustomOidcUserInfo.Builder email(String email) {
            return this.claim("email", email);
        }
        
        public CustomOidcUserInfo.Builder profile(String profile) {
            return this.claim("profile", profile);
        }
        
        public CustomOidcUserInfo build() {
            return new CustomOidcUserInfo(this.claims);
        }
        
    }
}
