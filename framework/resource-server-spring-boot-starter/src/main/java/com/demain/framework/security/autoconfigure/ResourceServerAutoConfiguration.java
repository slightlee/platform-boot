package com.demain.framework.security.autoconfigure;

import com.demain.framework.security.handler.CustomAccessDeniedHandler;
import com.demain.framework.security.handler.CustomeAuthenticationEntryPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 资源服务器自动配置
 *
 * @author demain_lee
 * @since 0.0.1
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(ResourceServerProperties.class)
@ComponentScan(basePackages = "com.demain.framework.security.autoconfigure")
@ConditionalOnProperty(prefix = "platform.resource.server", name = "enabled", havingValue = "true",
        matchIfMissing = true)
public class ResourceServerAutoConfiguration {
    
    private static final Logger log = LoggerFactory.getLogger(ResourceServerAutoConfiguration.class);
    
    private final ResourceServerProperties properties;
    
    public ResourceServerAutoConfiguration(ResourceServerProperties properties) {
        this.properties = properties;
    }
    
    /**
     * 方法安全配置 灵活配置 @EnableMethodSecurity 等价于 @EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
     */
    @Configuration
    @ConditionalOnProperty(prefix = "platform.resource.server", name = "enable-method-security", havingValue = "true",
            matchIfMissing = true)
    @EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
    static class MethodSecurityConfig {
        
    }
    
    @Bean
    @ConditionalOnMissingBean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeHttpRequests(authorize -> {
                configureWhitelist(authorize);
                authorize.anyRequest().authenticated();
            })
            .oauth2ResourceServer(oauth2 ->
                oauth2
                    .jwt(Customizer.withDefaults())
                    .accessDeniedHandler(new CustomAccessDeniedHandler())
                    .authenticationEntryPoint(new CustomeAuthenticationEntryPoint()) 
            );
        // @formatter:on
        return http.build();
    }
    
    /**
     * 配置白名单
     */
    private void configureWhitelist(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorize) {
        if (properties.getWhitelist().length > 0) {
            log.info("Configuring whitelist paths: {}", String.join(", ", properties.getWhitelist()));
            authorize.requestMatchers(properties.getWhitelist()).permitAll();
        }
    }
    
}