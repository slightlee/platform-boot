package com.demain.authorization.server.customize.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * <p>
 *
 * </p>
 *
 * @author demain_lee
 * @since 2024-12-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PlatformMenu extends BaseEntity {
    
    @Serial
    private static final long serialVersionUID = -3803656612161754831L;
    
    /**
     * 资源名称
     */
    private String menuName;
    
    /**
     * 父级ID
     */
    private Long parentId;
    
    /**
     * 跳转地址
     */
    private String url;
    
    /**
     * 所需权限
     */
    private String requiredPermissions;
    
    /**
     * 0:菜单,1:接口
     */
    private Integer type;
    
}
