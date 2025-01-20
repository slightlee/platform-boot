package com.demain.authorization.server.authentication.sms;

import com.demain.authorization.server.constant.SecurityConstants;
import com.demain.framework.security.response.ResponseCode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2TokenEndpointConfigurer;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 短信验证码模式认证提供者
 *
 * @author demain_lee
 * @see OAuth2AuthorizationCodeAuthenticationProvider 参考
 * @see OAuth2TokenEndpointConfigurer 参考 createDefaultAuthenticationProviders
 * @see JwtGenerator 参考
 * @since 2025/01/07
 */
public class SmsGrantAuthenticationProvider implements AuthenticationProvider {
    
    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";
    
    private static final OAuth2TokenType ID_TOKEN_TOKEN_TYPE = new OAuth2TokenType(OidcParameterNames.ID_TOKEN);
    
    private final UserDetailsService userDetailsService;
    
    private final Log logger = LogFactory.getLog(getClass());
    
    private final OAuth2AuthorizationService authorizationService;
    
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
    
    public SmsGrantAuthenticationProvider(UserDetailsService userDetailsService,
                                          OAuth2AuthorizationService authorizationService,
                                          OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        this.userDetailsService = userDetailsService;
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
    }
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
        SmsGrantAuthenticationToken smsCodeGrantAuthentication =
                (SmsGrantAuthenticationToken) authentication;
        
        OAuth2ClientAuthenticationToken clientPrincipal =
                getAuthenticatedClientElseThrowInvalidClient(smsCodeGrantAuthentication);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
        
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Retrieved registered client");
        }
        
        AuthorizationGrantType authorizationGrantType = smsCodeGrantAuthentication.getGrantType();
        Map<String, Object> additionalParameters = smsCodeGrantAuthentication.getAdditionalParameters();
        
        String username = (String) additionalParameters.get(OAuth2ParameterNames.USERNAME);
        String smsVerificationCode = (String) additionalParameters.get(SecurityConstants.SMS_VERIFICATION_CODE);
        
        // 请求参数权限范围
        String requestScopesStr = (String) additionalParameters.get(OAuth2ParameterNames.SCOPE);
        // 请求参数权限范围集合
        Set<String> requestScopeSet = Stream.of(requestScopesStr.split(" ")).collect(Collectors.toSet());
        
        // Ensure the client is configured to use this authorization grant type
        assert registeredClient != null;
        if (!registeredClient.getAuthorizationGrantTypes().contains(authorizationGrantType)) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }
        
        // 实际业务场景中，短信验证码应该通过短信服务发送给用户，用户输入后再进行校验
        if (!SecurityConstants.SMS_CODE_VALUE.equals(smsVerificationCode)) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error(ResponseCode.USER_VERIFICATION_CODE_INCORRECT.getCode(),
                            ResponseCode.USER_VERIFICATION_CODE_INCORRECT.getMessage(), ERROR_URI));
        }
        
        // 验证手机号
        UserDetails userDetails = null;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            throw new OAuth2AuthenticationException(new OAuth2Error(ResponseCode.USER_ACCOUNT_NOT_FOUND.getCode(),
                    ResponseCode.USER_ACCOUNT_NOT_FOUND.getMessage(), ERROR_URI));
        }
        
        // 构建一个已认证的对象UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                UsernamePasswordAuthenticationToken.authenticated(userDetails, clientPrincipal,
                        userDetails.getAuthorities());
        
        // Generate the access token
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(usernamePasswordAuthenticationToken)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizedScopes(requestScopeSet)
                .authorizationGrantType(authorizationGrantType)
                .authorizationGrant(smsCodeGrantAuthentication);
        
        // Initialize the OAuth2Authorization
        OAuth2Authorization.Builder authorizationBuilder =
                OAuth2Authorization.withRegisteredClient(registeredClient)
                        .principalName(clientPrincipal.getName())
                        .authorizedScopes(requestScopeSet)
                        .attribute(Principal.class.getName(), usernamePasswordAuthenticationToken)
                        .authorizationGrantType(authorizationGrantType);
        
        // ----- Access token -----
        OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
        if (generatedAccessToken == null) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the access token.", ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }
        
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Generated access token");
        }
        
        OAuth2AccessToken accessToken =
                new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                        generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
                        generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());
        if (generatedAccessToken instanceof ClaimAccessor) {
            authorizationBuilder.token(accessToken,
                    (metadata) -> metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME,
                            ((ClaimAccessor) generatedAccessToken).getClaims()));
        } else {
            authorizationBuilder.accessToken(accessToken);
        }
        
        // ----- Refresh token -----
        OAuth2RefreshToken refreshToken = null;
        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN) &&
        // Do not issue refresh token to public client
                !clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {
            tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
            OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
            if (generatedRefreshToken != null) {
                if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                    OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                            "The token generator failed to generate a valid refresh token.", ERROR_URI);
                    throw new OAuth2AuthenticationException(error);
                }
                
                if (this.logger.isTraceEnabled()) {
                    this.logger.trace("Generated refresh token");
                }
                
                refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
                authorizationBuilder.refreshToken(refreshToken);
            }
        }
        
        // ----- ID token -----
        /**
         * 此种方式生成的 id_token 会导致刷新 access token时报错，原因生成的 id_token 缺少 auth_time（认证时间）、sid（sessionId） 解决方案 可以通过自定义实现
         * OAuth2TokenCustomizer 见
         * https://docs.spring.io/spring-authorization-server/reference/core-model-components.html#oauth2-token-customizer
         */
        OidcIdToken idToken;
        Set<String> scopes = getInterseSet(registeredClient.getScopes(), requestScopeSet);
        if (scopes.contains(OidcScopes.OPENID)) {
            // @formatter:off
            tokenContext = tokenContextBuilder
                    .tokenType(ID_TOKEN_TOKEN_TYPE)
                    .authorization(authorizationBuilder.build())	// ID token customizer may need access to the access token and/or refresh token
                    .build();
            // @formatter:on
            
            OAuth2Token generatedIdToken = this.tokenGenerator.generate(tokenContext);
            if (!(generatedIdToken instanceof Jwt)) {
                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                        "The token generator failed to generate the ID token.", ERROR_URI);
                throw new OAuth2AuthenticationException(error);
            }
            
            if (this.logger.isTraceEnabled()) {
                this.logger.trace("Generated id token");
            }
            
            idToken = new OidcIdToken(generatedIdToken.getTokenValue(), generatedIdToken.getIssuedAt(),
                    generatedIdToken.getExpiresAt(), ((Jwt) generatedIdToken).getClaims());
            authorizationBuilder.token(idToken,
                    (metadata) -> metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME,
                            idToken.getClaims()));
        } else {
            idToken = null;
        }
        
        OAuth2Authorization authorization = authorizationBuilder.build();
        // Save the OAuth2Authorization
        this.authorizationService.save(authorization);
        
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Saved authorization");
        }
        
        if (idToken != null) {
            additionalParameters = new HashMap<>();
            additionalParameters.put(OidcParameterNames.ID_TOKEN, idToken.getTokenValue());
        }
        
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Authenticated token request");
        }
        
        return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken,
                refreshToken, additionalParameters);
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsGrantAuthenticationToken.class.isAssignableFrom(authentication);
    }
    
    static OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient(Authentication authentication) {
        OAuth2ClientAuthenticationToken clientPrincipal = null;
        if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication.getPrincipal().getClass())) {
            clientPrincipal = (OAuth2ClientAuthenticationToken) authentication.getPrincipal();
        }
        if (clientPrincipal != null && clientPrincipal.isAuthenticated()) {
            return clientPrincipal;
        }
        throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
    }
    
    static Set<String> getInterseSet(Set<String> set1, Set<String> set2) {
        if (CollectionUtils.isEmpty(set1) || CollectionUtils.isEmpty(set2)) {
            return Set.of();
        }
        Set<String> set = set1.stream().filter(set2::contains).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(set)) {
            set = Set.of();
        }
        return set;
    }
    
}
