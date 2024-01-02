package com.demain.server.admin.controller;


import com.demain.server.admin.entity.PlatformRole;
import com.demain.server.admin.service.PlatformRoleService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author demain_lee
 * @since 2021-12-14
 */
@Tag(name = "角色接口")
@ApiSupport(order = 20,author = "demain_lee")
@RestController
@RequestMapping("/admin/role")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PlatformRoleController {

    private final PlatformRoleService platformRoleService;

    @Operation(summary =  "角色列表")
    @ApiOperationSupport(order = 1,author = "demain_lee")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<PlatformRole> list(){
        return platformRoleService.list();
    }

    @Operation(summary =  "添加角色")
    @ApiOperationSupport(order = 2,author = "demain_lee")
    @RequestMapping(value = "/saveRole",method = RequestMethod.POST)
    public Boolean saveRole(@RequestBody PlatformRole platformRole){
        return platformRoleService.save(platformRole);
    }

    @Operation(summary =  "编辑角色")
    @ApiOperationSupport(order = 3,author = "demain_lee")
    @RequestMapping(value = "/updateRole",method = RequestMethod.PUT)
    public Boolean updateRole(@RequestBody PlatformRole platformRole){
        return platformRoleService.updateById(platformRole);
    }

    @Operation(summary =  "删除角色")
    @ApiOperationSupport(order = 4,author = "demain_lee")
    @RequestMapping(value = "/deleteRole",method = RequestMethod.DELETE)
    public Boolean deleteRole(@RequestParam List<Long> ids){
        return platformRoleService.removeByIds(ids);
    }
}

