package com.demain.server.admin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="PlatformUser对象", description="用户表")
public class PlatformUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "1 男  0 女  2 未知")
    private String gender;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "用户状态 0 启用 1 禁用")
    private Integer status;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "备注")
    private String remark;

}
