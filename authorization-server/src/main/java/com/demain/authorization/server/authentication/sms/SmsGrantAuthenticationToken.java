package com.demain.authorization.server.authentication.sms;

import com.demain.authorization.server.constant.SecurityConstants;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.util.SpringAuthorizationServerVersion;

import java.io.Serial;
import java.util.Map;

/**
 * 短信验证码模式token
 *
 * @author demain_lee
 * @since 2025/01/07
 */
public class SmsGrantAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {
    
    @Serial
    private static final long serialVersionUID = SpringAuthorizationServerVersion.SERIAL_VERSION_UID;
    
    protected SmsGrantAuthenticationToken(
            Authentication clientPrincipal,
            @Nullable Map<String, Object> additionalParameters) {
        super(new AuthorizationGrantType(SecurityConstants.GRANT_TYPE_SMS), clientPrincipal, additionalParameters);
    }
}
