package com.demain.framework.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security 配置
 * <p>
 * {@link org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration.WebSecurityEnablerConfiguration}
 * WebSecurityEnablerConfiguration 是用来判断项目中是否使用了@EnableWebSecurity注解，如果没有使用，则添加。
 * 也就是说我们自定义spring security配置类时，可以不用添加@EnableWebSecurity注解
 *
 * @author demain_lee
 * @since 2024/2/27
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    /**
     * 过滤器链
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults()) // CsrfFilter
                .authorizeHttpRequests(authorize -> // AuthorizationFilter
                authorize
                        .requestMatchers(
                                "/index",
                                "/login",
                                "/doc.html",
                                "/webjars/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-resources/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults()) // BasicAuthenticationFilter
                // .formLogin(Customizer.withDefaults()) // Form表单认证的默认配置 UsernamePasswordAuthenticationFilter 以及 DefaultLogoutPageGeneratingFilter 和 DefaultLogoutPageGeneratingFilter
                .formLogin(login -> login
                        .loginPage("/login").permitAll());
        return http.build();
    }
    
    /**
     * 密码编码器
     *
     * @return passwordEncoder  密码格式：默认 {bcrypt}$2a$10$zwqwIV5NR6FPtg6.ievmYeMwVZAhErmC.LsHMUsTvgoqakiMX9q3W
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
