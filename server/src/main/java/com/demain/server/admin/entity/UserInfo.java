package com.demain.server.admin.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name="用户信息", description="用户信息")
public class UserInfo {

    /**
     * 用户信息
     */
    @Schema(description = "用户信息")
    private PlatformUser platformUser;

    /**
     * 角色集合
     */
    @Schema(description = "角色集合")
    private List<String> roleList;
}
