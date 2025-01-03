package com.demain.authorization.server.customize.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author demain_lee
 * @since 2021-12-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PlatformUser extends BaseEntity {
    
    @Serial
    private static final long serialVersionUID = -819255405219478526L;
    
    private String account;
    
    private String password;
    
    private String nickname;
    
    private String realName;
    
    private String gender;
    
    private String email;
    
    private String phone;
    
    private Integer status;
    
    private String avatar;
    
    private String remark;
    
}
