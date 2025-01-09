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
    
    public static Builder cusBuilder() {
        return new Builder();
    }
    
    public static final class Builder {
        
        private final Map<String, Object> claims = new LinkedHashMap<>();
        
        private Builder() {
        }
        
        public Builder claim(String name, Object value) {
            this.claims.put(name, value);
            return this;
        }
        
        public Builder claims(Consumer<Map<String, Object>> claimsConsumer) {
            claimsConsumer.accept(this.claims);
            return this;
        }
        
        public Builder userName(String username) {
            return this.claim("username", username);
        }
        
        public Builder name(String name) {
            return this.claim("name", name);
        }
        
        public Builder nickname(String nickname) {
            return this.claim("nickname", nickname);
        }
        
        public Builder avatar(String avatar) {
            return this.claim("avatar", avatar);
        }
        
        public Builder status(Integer status) {
            return this.claim("status", status);
        }
        
        public Builder phoneNumber(String phoneNumber) {
            return this.claim("phone_number", phoneNumber);
        }
        
        public Builder email(String email) {
            return this.claim("email", email);
        }
        
        public Builder profile(String profile) {
            return this.claim("profile", profile);
        }
        
        public CustomOidcUserInfo build() {
            return new CustomOidcUserInfo(this.claims);
        }
        
    }
}
