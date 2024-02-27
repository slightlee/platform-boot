package com.demain.framework.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security 配置
 *
 * @author demain_lee
 * @since 2024/2/27
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())       // CsrfFilter
                .authorizeHttpRequests(authorize ->    // AuthorizationFilter
                        authorize
                                .requestMatchers(
                                        "/index",
                                        "/loginPage",
                                        "/doc.html",
                                        "/webjars/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-resources/**"
                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())  // BasicAuthenticationFilter
//                .formLogin(Customizer.withDefaults())  // Form表单认证的默认配置 UsernamePasswordAuthenticationFilter 以及 DefaultLogoutPageGeneratingFilter 和 DefaultLogoutPageGeneratingFilter
                .formLogin(login ->
                        login
                                .loginPage("/loginPage").permitAll()
                                .loginProcessingUrl("/login")
                )
        ;
        return http.build();
    }
}
