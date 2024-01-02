package com.demain.server.admin.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author demain_lee
 * @since 2021-12-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(name="PlatformRole对象", description="角色表")
public class PlatformRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "父id")
    private Long parentId;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色标识")
    private String roleKey;

    @Schema(description = "角色级别")
    private Integer roleLevel;

    @Schema(description = "0 启用 1 禁用")
    private Integer roleStatus;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "备注")
    private String remark;

}
