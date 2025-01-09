package com.demain.authorization.server.authentication.core;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.Assert;

import java.io.Serial;
import java.io.Serializable;

/**
 * 自定义授权类型
 *
 * @author demain_lee
 * @since 2025/01/09
 */
public final class CustomAuthorizationGrantType implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 620L;
    
    public static final AuthorizationGrantType SMS_VERIFICATION_CODE =
            new AuthorizationGrantType("sms_verification_code");
    
    private final String value;
    
    public CustomAuthorizationGrantType(String value) {
        Assert.hasText(value, "value cannot be empty");
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && this.getClass() == obj.getClass()) {
            AuthorizationGrantType that = (AuthorizationGrantType) obj;
            return this.getValue().equals(that.getValue());
        } else {
            return false;
        }
    }
    
    public int hashCode() {
        return this.getValue().hashCode();
    }
    
}
