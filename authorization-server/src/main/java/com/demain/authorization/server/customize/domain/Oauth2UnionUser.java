package com.demain.authorization.server.customize.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 联合登录信息
 *
 * @author demain_lee
 * @since 0.0.1
 */
@Data
public class Oauth2UnionUser {
    
    /**
     * 唯一标识
     */
    private String uniqueId;
    
    /**
     * 第三方登录账号
     */
    private String uniqueAccount;
    
    /**
     * 用户名、昵称
     */
    private String nickName;
    
    /**
     * 头像地址
     */
    private String avatarUrl;
    
    private String email;
    
    /**
     * 登录平台
     */
    private String platform;
    
    /**
     * 三方登录获取的认证信息
     */
    private String credentials;
    
    /**
     * 三方登录获取的认证信息的过期时间
     */
    private LocalDateTime credentialsExpiresAt;
    
    private String userNameAttributeName;
    
}
