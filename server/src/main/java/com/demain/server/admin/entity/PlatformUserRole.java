package com.demain.server.admin.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户和角色关联表
 * </p>
 *
 * @author demain_lee
 * @since 2021-12-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(name = "PlatformUserRole对象", description = "用户和角色关联表")
public class PlatformUserRole extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "用户id")
    private Long userId;
    
    @Schema(description = "角色id")
    private Long roleId;
    
}
