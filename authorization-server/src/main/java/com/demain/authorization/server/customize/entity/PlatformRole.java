package com.demain.authorization.server.customize.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author demain_lee
 * @since 2021-12-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PlatformRole extends BaseEntity {
    
    @Serial
    private static final long serialVersionUID = -7169949107249113061L;
    
    private Long parentId;
    
    private String roleName;
    
    private String roleKey;
    
    private Integer roleLevel;
    
    private Integer roleStatus;
    
    private Integer sort;
    
    private String remark;
    
}
