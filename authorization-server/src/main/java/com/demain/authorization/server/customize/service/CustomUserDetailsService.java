package com.demain.authorization.server.customize.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    /**
     * UserDetailsService 另一种实现，用于检索要进行身份验证的用户
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // todo 改为从数据库获取用户信息
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserDetails userDetails = User.withUsername("user")
                .password(passwordEncoder.encode("123456"))
                .roles("USER")
                .build();
        return userDetails;
    }
}
