package com.demain.authorization.server.authentication.oidc;

import java.util.*;
import java.util.function.Function;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.util.Assert;

public class CustomOidcUserInfoAuthenticationProvider implements AuthenticationProvider {
    
    private final Log logger = LogFactory.getLog(this.getClass());
    private final OAuth2AuthorizationService authorizationService;
    private Function<OidcUserInfoAuthenticationContext, CustomOidcUserInfo> userInfoMapper =
            new CustomOidcUserInfoAuthenticationProvider.DefaultOidcUserInfoMapper();
    
    public CustomOidcUserInfoAuthenticationProvider(OAuth2AuthorizationService authorizationService) {
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        this.authorizationService = authorizationService;
    }
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OidcUserInfoAuthenticationToken userInfoAuthentication =
                (OidcUserInfoAuthenticationToken) authentication;
        AbstractOAuth2TokenAuthenticationToken<?> accessTokenAuthentication = null;
        if (AbstractOAuth2TokenAuthenticationToken.class
                .isAssignableFrom(userInfoAuthentication.getPrincipal().getClass())) {
            accessTokenAuthentication =
                    (AbstractOAuth2TokenAuthenticationToken) userInfoAuthentication.getPrincipal();
        }
        
        if (accessTokenAuthentication != null && accessTokenAuthentication.isAuthenticated()) {
            String accessTokenValue = accessTokenAuthentication.getToken().getTokenValue();
            OAuth2Authorization authorization =
                    this.authorizationService.findByToken(accessTokenValue, OAuth2TokenType.ACCESS_TOKEN);
            if (authorization == null) {
                throw new OAuth2AuthenticationException("invalid_token");
            } else {
                if (this.logger.isTraceEnabled()) {
                    this.logger.trace("Retrieved authorization with access token");
                }
                
                OAuth2Authorization.Token<OAuth2AccessToken> authorizedAccessToken =
                        authorization.getAccessToken();
                if (!authorizedAccessToken.isActive()) {
                    throw new OAuth2AuthenticationException("invalid_token");
                } else if (!((OAuth2AccessToken) authorizedAccessToken.getToken()).getScopes()
                        .contains("openid")) {
                    throw new OAuth2AuthenticationException("insufficient_scope");
                } else {
                    OAuth2Authorization.Token<OidcIdToken> idToken =
                            authorization.getToken(OidcIdToken.class);
                    if (idToken == null) {
                        throw new OAuth2AuthenticationException("invalid_token");
                    } else {
                        if (this.logger.isTraceEnabled()) {
                            this.logger.trace("Validated user info request");
                        }
                        
                        OidcUserInfoAuthenticationContext authenticationContext =
                                OidcUserInfoAuthenticationContext.with(userInfoAuthentication)
                                        .accessToken((OAuth2AccessToken) authorizedAccessToken.getToken())
                                        .authorization(authorization).build();
                        OidcUserInfo userInfo = (OidcUserInfo) this.userInfoMapper.apply(authenticationContext);
                        if (this.logger.isTraceEnabled()) {
                            this.logger.trace("Authenticated user info request");
                        }
                        
                        return new OidcUserInfoAuthenticationToken(accessTokenAuthentication, userInfo);
                    }
                }
            }
        } else {
            throw new OAuth2AuthenticationException("invalid_token");
        }
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return OidcUserInfoAuthenticationToken.class.isAssignableFrom(authentication);
    }
    
    public void setUserInfoMapper(
            Function<OidcUserInfoAuthenticationContext, CustomOidcUserInfo> userInfoMapper) {
        Assert.notNull(userInfoMapper, "userInfoMapper cannot be null");
        this.userInfoMapper = userInfoMapper;
    }
    
    private static final class DefaultOidcUserInfoMapper
            implements Function<OidcUserInfoAuthenticationContext, CustomOidcUserInfo> {
        
        private static final List<String> EMAIL_CLAIMS = Arrays.asList("email", "email_verified");
        private static final List<String> PHONE_CLAIMS =
                Arrays.asList("phone_number", "phone_number_verified");
        private static final List<String> PROFILE_CLAIMS =
                Arrays.asList("name", "username", "status", "profile");
        
        private DefaultOidcUserInfoMapper() {
        }
        
        public CustomOidcUserInfo apply(OidcUserInfoAuthenticationContext authenticationContext) {
            OAuth2Authorization authorization = authenticationContext.getAuthorization();
            OidcIdToken idToken = (OidcIdToken) authorization.getToken(OidcIdToken.class).getToken();
            OAuth2AccessToken accessToken = authenticationContext.getAccessToken();
            Map<String, Object> scopeRequestedClaims =
                    getClaimsRequestedByScope(idToken.getClaims(), accessToken.getScopes());
            return new CustomOidcUserInfo(scopeRequestedClaims);
        }
        
        private static Map<String, Object> getClaimsRequestedByScope(Map<String, Object> claims,
                Set<String> requestedScopes) {
            Set<String> scopeRequestedClaimNames = new HashSet<>(32);
            scopeRequestedClaimNames.add("sub");
            if (requestedScopes.contains("address")) {
                scopeRequestedClaimNames.add("address");
            }
            
            if (requestedScopes.contains("email")) {
                scopeRequestedClaimNames.addAll(EMAIL_CLAIMS);
            }
            
            if (requestedScopes.contains("phone")) {
                scopeRequestedClaimNames.addAll(PHONE_CLAIMS);
            }
            
            if (requestedScopes.contains("profile")) {
                scopeRequestedClaimNames.addAll(PROFILE_CLAIMS);
            }
            
            Map<String, Object> requestedClaims = new HashMap<>(claims);
            requestedClaims.keySet()
                    .removeIf((claimName) -> !scopeRequestedClaimNames.contains(claimName));
            return requestedClaims;
        }
    }
}
