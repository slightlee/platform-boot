package com.demain.server.admin.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author demain_lee
 * @since 2021-12-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(name = "PlatformUser对象", description = "用户表")
public class PlatformUser extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "账号")
    private String account;
    
    @Schema(description = "密码")
    private String password;
    
    @Schema(description = "昵称")
    private String nickname;
    
    @Schema(description = "真实姓名")
    private String realName;
    
    @Schema(description = "1 男  0 女  2 未知")
    private String gender;
    
    @Schema(description = "邮箱")
    private String email;
    
    @Schema(description = "电话")
    private String phone;
    
    @Schema(description = "用户状态 0 启用 1 禁用")
    private Integer status;
    
    @Schema(description = "头像")
    private String avatar;
    
    @Schema(description = "备注")
    private String remark;
    
}
