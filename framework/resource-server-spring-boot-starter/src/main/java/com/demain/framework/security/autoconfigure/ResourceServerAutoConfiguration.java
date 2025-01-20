package com.demain.framework.security.autoconfigure;

import com.demain.framework.security.handler.CustomAccessDeniedHandler;
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
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(ResourceServerProperties.class)
@ComponentScan(basePackages = "com.demain.framework.security.autoconfigure")
@ConditionalOnProperty(prefix = "platform.resource.server", name = "enabled", havingValue = "true",
        matchIfMissing = true)
public class ResourceServerAutoConfiguration {
    
    
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
        http.authorizeHttpRequests(authorize ->
                authorize.anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 ->
                oauth2
                    .jwt(Customizer.withDefaults())
                    .accessDeniedHandler(new CustomAccessDeniedHandler()));
        // @formatter:on
        return http.build();
    }
    
}