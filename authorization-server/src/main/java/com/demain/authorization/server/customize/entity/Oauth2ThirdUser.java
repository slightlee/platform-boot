package com.demain.authorization.server.customize.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 第三方登录用户信息
 */
@Data
public class Oauth2ThirdUser {
    
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private Long userId;
    
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
    private String uniqueName;
    
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
    
    private LocalDateTime updateTime;
    
    private LocalDateTime createTime;
    
}
