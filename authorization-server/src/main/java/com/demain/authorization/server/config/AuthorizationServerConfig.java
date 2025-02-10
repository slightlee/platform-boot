package com.demain.authorization.server.config;

import com.demain.authorization.server.authentication.core.CustomAuthorizationGrantType;
import com.demain.authorization.server.authentication.device.DeviceClientAuthenticationConverter;
import com.demain.authorization.server.authentication.device.DeviceClientAuthenticationProvider;
import com.demain.authorization.server.authentication.oidc.CustomOidcUserInfoAuthenticationConverter;
import com.demain.authorization.server.authentication.oidc.CustomOidcUserInfoAuthenticationProvider;
import com.demain.authorization.server.authentication.oidc.CustomOidcUserInfoService;
import com.demain.authorization.server.authentication.password.PasswordGrantAuthenticationConverter;
import com.demain.authorization.server.authentication.password.PasswordGrantAuthenticationProvider;
import com.demain.authorization.server.authentication.sms.SmsGrantAuthenticationConverter;
import com.demain.authorization.server.authentication.sms.SmsGrantAuthenticationProvider;
import com.demain.authorization.server.handler.GrantTypeHandler;
import com.demain.authorization.server.handler.GrantTypeHandlerRegistry;
import com.demain.authorization.server.jose.Jwks;
import com.demain.framework.security.handler.CustomAuthenticationFailureHandler;
import com.demain.framework.security.handler.CustomeAuthenticationEntryPoint;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.apache.catalina.util.StandardSessionIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * 授权服务器配置
 *
 * @author demain_lee
 * @since 2025/01/08
 */
@Configuration
public class AuthorizationServerConfig {
    
    private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";
    
    private final UserDetailsService userDetailsService;
    
    private final CustomOidcUserInfoService customOidcUserInfoService;
    
    private final GrantTypeHandlerRegistry registry;
    
    public AuthorizationServerConfig(UserDetailsService userDetailsService,
                                     CustomOidcUserInfoService customOidcUserInfoService,
                                     GrantTypeHandlerRegistry registry) {
        this.userDetailsService = userDetailsService;
        this.customOidcUserInfoService = customOidcUserInfoService;
        this.registry = registry;
    }
    
    /**
     * 协议端点的 Spring Security 过滤链
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http,
            RegisteredClientRepository registeredClientRepository,
            AuthorizationServerSettings authorizationServerSettings,
            OAuth2AuthorizationService authorizationService,
            OAuth2TokenGenerator<?> tokenGenerator) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        
        DeviceClientAuthenticationConverter deviceClientAuthenticationConverter =
                new DeviceClientAuthenticationConverter(
                        authorizationServerSettings.getDeviceAuthorizationEndpoint());
        DeviceClientAuthenticationProvider deviceClientAuthenticationProvider =
                new DeviceClientAuthenticationProvider(registeredClientRepository);
        
        // @formatter:off
        http
            .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                // 设备码用户验证url(自定义用户验证页)
                .deviceAuthorizationEndpoint(deviceAuthorizationEndpoint ->
                        deviceAuthorizationEndpoint.verificationUri("/activate"))
                // 验证设备码用户确认页面
                .deviceVerificationEndpoint(deviceVerificationEndpoint ->
                        deviceVerificationEndpoint.consentPage(CUSTOM_CONSENT_PAGE_URI))
                .clientAuthentication(clientAuthentication ->
                    // 客户端认证添加设备码的converter和provider
                    clientAuthentication
                        .authenticationConverter(deviceClientAuthenticationConverter)
                        .authenticationProvider(deviceClientAuthenticationProvider)
                )
                //自定义授权确认页面
                .authorizationEndpoint(authorizationEndpoint ->
                    authorizationEndpoint.consentPage(CUSTOM_CONSENT_PAGE_URI))
                // 密码模式
                .tokenEndpoint(tokenEndpoint ->
                    tokenEndpoint
                        .accessTokenRequestConverter(
                                new PasswordGrantAuthenticationConverter())
                        .authenticationProvider(
                                new PasswordGrantAuthenticationProvider(userDetailsService,passwordEncoder(),
                                        authorizationService, tokenGenerator)))
                // 短信验证码模式
                .tokenEndpoint(tokenEndpoint -> 
                        tokenEndpoint.accessTokenRequestConverter(
                                new SmsGrantAuthenticationConverter())
                                .authenticationProvider(
                                new SmsGrantAuthenticationProvider(userDetailsService, 
                                        authorizationService, tokenGenerator)))
                .tokenEndpoint(tokenEndpoint->{
                    tokenEndpoint.errorResponseHandler(new CustomAuthenticationFailureHandler());
                })
                .clientAuthentication(clientAuthentication -> {
                    clientAuthentication.errorResponseHandler(new CustomAuthenticationFailureHandler());
                })
                .oidc(Customizer.withDefaults()); // 开启 openid connect
//                .oidc(oidcCustomizer-> {
//                  oidcCustomizer.userInfoEndpoint(userInfoEndpointCustomizer -> {
//                    userInfoEndpointCustomizer.userInfoRequestConverter(
//                            new CustomOidcUserInfoAuthenticationConverter(customOidcUserInfoService));
//                    userInfoEndpointCustomizer.authenticationProvider(
//                            new CustomOidcUserInfoAuthenticationProvider(authorizationService));
//                  });
//                });
        //  未通过授权端点验证时重定向到登录页面
        http
            .exceptionHandling(exception ->
                exception
                    .defaultAuthenticationEntryPointFor(
                        new LoginUrlAuthenticationEntryPoint("/login"),
                        new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                    )
                    .authenticationEntryPoint(new CustomeAuthenticationEntryPoint())
            )
            // 接受用户信息或客户注册的访问令牌
            .oauth2ResourceServer(resourceServer ->
                resourceServer
                    .jwt(Customizer.withDefaults()));
        // @formatter:on
        return http.build();
    }
    
    /**
     * 配置密码解析器，使用BCrypt的方式对密码进行加密和验证
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * 用于管理客户端的 RegisteredClientRepository 实例
     *
     * @param passwordEncoder 密码管理器
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcOperations jdbcOperations,
            PasswordEncoder passwordEncoder) {
        // @formatter:off
        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("oidc-client")
            .clientSecret(passwordEncoder.encode("123456"))
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .redirectUri("http://127.0.0.1:8080/login/oauth2/code/oidc-client")
            .postLogoutRedirectUri("http://127.0.0.1:8080/")
            .scope(OidcScopes.OPENID)
            .scope(OidcScopes.PROFILE)
            .scope("user.info")
            .scope("all")
             // 客户端设置，设置用户需要确认授权，设置false后不需要确认
            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
            //设置accessToken有效期
            .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofHours(2)).build())
            .build();

        JdbcRegisteredClientRepository clientRepository = new JdbcRegisteredClientRepository(jdbcOperations);
        RegisteredClient registeredClient = clientRepository.findByClientId(oidcClient.getClientId());
        if (registeredClient == null) {
            clientRepository.save(oidcClient);
        }

        // 设备码授权客户端
        RegisteredClient deviceClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("device-client")
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                .authorizationGrantType(AuthorizationGrantType.DEVICE_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scope("user.info")
                .build();

        RegisteredClient deviceRegisteredClient = clientRepository.findByClientId(deviceClient.getClientId());
        if (deviceRegisteredClient == null) {
            clientRepository.save(deviceClient);
        }

        /**
         * 由于公共客户端没法安全的保存 client_secret，所以在实际应用中，公共客户端 连 client_secret 都没必要存，
         * 所以SpringAuthorizationServer定义一个 none 方式来表示这种情况。
         * <p/>
         * PKCE 是授权码流程的扩展，用于防止 CSRF 和授权码(code)注入攻击。
         * PKCE 一般都伴随着授权码模式使用，可称之为 增强版授权码流程，又称 Authorization Code with PKCE Flow 。
         * RegisteredClientRepository 主要用于管理第三方应用的信息 授权码模式：
         * <p/>
         * 获取code
         * {@code http://127.0.0.1:9000/oauth2/authorize?client_id=pkce-client&response_type=code&scope=message.read+message.write&redirect_uri=http://127.0.0.1:8080/login/oauth2/code/pkce-client&code_challenge=awdQbZfQnpYJ4voM4HQe6LFITvHBK2OTde09taqVBFY&code_challenge_method=S256 }
         */

        // PKCE客户端
        RegisteredClient pkceClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("pkce-client")
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/pkce-client")
                .clientSettings(ClientSettings.builder()
                        // 公共客户端（NONE方式认证）必须开启 PKCE 流程
                        .requireProofKey(Boolean.TRUE)
                        // 授权码模式需要用户手动授权！false表示默认通过
                        .requireAuthorizationConsent(true)
                        .build())
                // 自定scope
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope("user.info")
                .build();
        RegisteredClient pkceRegisteredClient = clientRepository.findByClientId(pkceClient.getClientId());
        if (pkceRegisteredClient == null) {
            clientRepository.save(pkceClient);
        }
        
        // 密码模式客户端
        RegisteredClient passwordClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("password-client")
                .clientSecret(passwordEncoder.encode("123456"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .postLogoutRedirectUri("http://127.0.0.1:9000/")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope("user.info")
                // 客户端设置，设置用户需要确认授权，设置false后不需要确认
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                //设置accessToken有效期
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofHours(2)).build())
                .build();
        
        RegisteredClient passwordRegisteredClient = clientRepository.findByClientId(passwordClient.getClientId());
        if (passwordRegisteredClient == null) {
            clientRepository.save(passwordClient);
        }
        
        // 短信验证码模式客户端
        RegisteredClient smsCodeClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("sms-code-client")
                .clientSecret(passwordEncoder.encode("123456"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(CustomAuthorizationGrantType.SMS_VERIFICATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .postLogoutRedirectUri("http://127.0.0.1:9000/")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                // 客户端设置，设置用户需要确认授权，设置false后不需要确认
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                //设置accessToken有效期
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofHours(2)).build())
                .build();
        
        RegisteredClient smsCodeRegisteredClient = clientRepository.findByClientId(smsCodeClient.getClientId());
        if (smsCodeRegisteredClient == null) {
            clientRepository.save(smsCodeClient);
        }
        
        RegisteredClient clientServer = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("client-server")  // 与 client-server 的配置匹配
                .clientSecret(passwordEncoder.encode("123456"))  // 与 client-server 的配置匹配
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://client-server:9003/login/oauth2/code/uaa")  // 与 client-server 的配置匹配
                .scope("profile")
                .scope("openid")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofHours(2)).build())
                .build();
        
        RegisteredClient clientServerRegisteredClient = clientRepository.findByClientId(clientServer.getClientId());
        if (clientServerRegisteredClient == null) {
            clientRepository.save(clientServer);
        }
        
        // @formatter:on
        return clientRepository;
    }
    
    /**
     * 授权管理服务配置
     *
     * @param jdbcTemplate 数据源信息
     * @param registeredClientRepository 客户端repository
     * @return JdbcOAuth2AuthorizationService
     */
    @Bean
    public OAuth2AuthorizationService auth2AuthorizationService(JdbcTemplate jdbcTemplate,
            RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
    }
    
    /**
     * 授权确认服务配置
     *
     * @param jdbcTemplate 数据源信息
     * @param registeredClientRepository 客户端repository
     * @return JdbcOAuth2AuthorizationConsentService
     */
    @Bean
    public OAuth2AuthorizationConsentService oAuth2AuthorizationConsentService(JdbcTemplate jdbcTemplate,
            RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }
    
    /**
     * 用于签署访问令牌的 com.nimbusds.jose.jwk.source.JWKSource 实例
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = Jwks.generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }
    
    /**
     * 用于解码已签名访问令牌的 JwtDecoder 实例
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }
    
    /**
     * 用于配置 Spring 授权服务器的 AuthorizationServerSettings 实例
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }
    
    /**
     * 配置token生成器
     * <p>
     * 也可使用 OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator = OAuth2ConfigurerUtils.getTokenGenerator(http);
     * <p>
     * org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2ConfigurerUtils;
     */
    @Bean
    OAuth2TokenGenerator<?> tokenGenerator(JWKSource<SecurityContext> jwkSource) {
        JwtGenerator jwtGenerator = new JwtGenerator(new NimbusJwtEncoder(jwkSource));
        jwtGenerator.setJwtCustomizer(jwtCustomizer(registry));
        OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
        OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(
                jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
    }
    
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(GrantTypeHandlerRegistry registry) {
        
        return context -> {
            JwtClaimsSet.Builder claims = context.getClaims();
            if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
                // Customize headers/claims for access_token
                String grantType = context.getAuthorizationGrantType().getValue();
                GrantTypeHandler handler = registry.getHandler(grantType);
                if (handler != null) {
                    handler.handle(context, claims);
                }
            } else if (context.getTokenType().getValue().equals(OidcParameterNames.ID_TOKEN)) {
                // Customize headers/claims for id_token
                claims.claim(IdTokenClaimNames.AUTH_TIME, Date.from(Instant.now()));
                StandardSessionIdGenerator standardSessionIdGenerator = new StandardSessionIdGenerator();
                claims.claim("sid", standardSessionIdGenerator.generateSessionId());
                claims.claim("username", context.getPrincipal().getName());
            }
        };
    }
    
    // @Bean
    // public EmbeddedDatabase embeddedDatabase() {
//        // @formatter:off
//        return new EmbeddedDatabaseBuilder()
//                .generateUniqueName(true)
//                .setType(EmbeddedDatabaseType.H2)
//                .setScriptEncoding("UTF-8")
//                .addScript("org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql")
//                .addScript("org/springframework/security/oauth2/server/authorization/oauth2-authorization-consent-schema.sql")
//                .addScript("org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql")
//                .build();
//        // @formatter:on
    // }
    
}
