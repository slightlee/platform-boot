package com.demain.authorization.server.authentication;

import java.util.Map;

import org.springframework.lang.Nullable;
import org.springframework.security.core.Transient;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

/**
 * 设备码模式token
 * 
 * @author demain_lee
 * @since 2024/09/12
 */
@Transient
public class DeviceClientAuthenticationToken extends OAuth2ClientAuthenticationToken {
    
    public DeviceClientAuthenticationToken(String clientId, ClientAuthenticationMethod clientAuthenticationMethod,
                                           @Nullable Object credentials,
                                           @Nullable Map<String, Object> additionalParameters) {
        super(clientId, clientAuthenticationMethod, credentials, additionalParameters);
    }
    
    public DeviceClientAuthenticationToken(RegisteredClient registeredClient,
                                           ClientAuthenticationMethod clientAuthenticationMethod,
                                           @Nullable Object credentials) {
        super(registeredClient, clientAuthenticationMethod, credentials);
    }
    
}
