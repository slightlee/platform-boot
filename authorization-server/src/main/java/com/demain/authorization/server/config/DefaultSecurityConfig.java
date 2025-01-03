package com.demain.authorization.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class DefaultSecurityConfig {
    
    /**
     * 用于身份验证的 Spring Security 过滤链
     */
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        
        // @formatter:off
        http
            .authorizeHttpRequests(authorize ->
                authorize
                    .requestMatchers("/assets/**", "/login","/oauth2/**","/getCaptcha").permitAll()
                    .anyRequest().authenticated()
            )
            // 表单登录处理从授权服务器过滤链重定向到登录页面的过程
            .formLogin(formLogin ->
                formLogin
                    .loginPage("/login")
            )
//            .oauth2Login(oauth2Login ->
//                oauth2Login
//                    .loginPage("/login")
//            )

            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
//            .formLogin(Customizer.withDefaults())
//            )
        ;
        // @formatter:on
        return http.build();
    }
    
    /**
     * UserDetailsService 实例，用于检索要进行身份验证的用户
     */
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails userDetails = User.withUsername("user")
//                .password(passwordEncoder.encode("123456"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(userDetails);
//    }
    
}
