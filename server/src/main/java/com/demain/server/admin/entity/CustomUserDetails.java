package com.demain.server.admin.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义 UserDetails 实现
 *
 * @author demain_lee
 * @since 2024/02/27
 */
public class CustomUserDetails implements UserDetails {
    
    @Serial
    private static final long serialVersionUID = 808434479496703190L;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 用户状态
     */
    private Integer status;
    
    /**
     * 角色集合
     */
    private List<PlatformUserRole> roleList;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleList.stream()
                .map(platformUserRole -> new SimpleGrantedAuthority(platformUserRole.getRoleId().toString()))
                .collect(Collectors.toList());
    }
    
    @Override
    public String getPassword() {
        return this.password;
    }
    
    @Override
    public String getUsername() {
        return this.username;
    }
    
    /**
     * 账户是否未过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    /**
     * 是否未被锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    /**
     * 密码是否未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    /**
     * 账户是否可用
     */
    @Override
    public boolean isEnabled() {
        return status == 0;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public void setRoleList(List<PlatformUserRole> roleList) {
        this.roleList = roleList;
    }
}
