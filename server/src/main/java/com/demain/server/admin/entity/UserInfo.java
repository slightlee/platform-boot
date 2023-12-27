package com.demain.server.admin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="用户信息", description="用户信息")
public class UserInfo {

    /**
     * 用户信息
     */
    @ApiModelProperty(value = "用户信息")
    private PlatformUser platformUser;

    /**
     * 角色集合
     */
    @ApiModelProperty(value = "角色集合")
    private List<String> roleList;
}
