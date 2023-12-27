package com.demain.server.admin.controller;


import com.demain.server.admin.entity.PlatformRole;
import com.demain.server.admin.service.PlatformRoleService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author demain_lee
 * @since 2021-12-14
 */
@Api(value = "角色接口", tags = "角色" )
@ApiSupport(order = 20,author = "demain_lee")
@RestController
@RequestMapping("/admin/role")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PlatformRoleController {

    private final PlatformRoleService platformRoleService;

    @ApiOperation(value = "角色列表")
    @ApiOperationSupport(order = 1,author = "demain_lee")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<PlatformRole> list(){
        return platformRoleService.list();
    }

    @ApiOperation(value = "添加角色")
    @ApiOperationSupport(order = 2,author = "demain_lee")
    @RequestMapping(value = "/saveRole",method = RequestMethod.POST)
    public Boolean saveRole(PlatformRole platformRole){
        return platformRoleService.save(platformRole);
    }

    @ApiOperation(value = "编辑角色")
    @ApiOperationSupport(order = 3,author = "demain_lee")
    @RequestMapping(value = "/updateRole",method = RequestMethod.PUT)
    public Boolean updateRole(PlatformRole platformRole){
        return platformRoleService.updateById(platformRole);
    }

    @ApiOperation(value = "删除角色")
    @ApiOperationSupport(order = 4,author = "demain_lee")
    @RequestMapping(value = "/deleteRole",method = RequestMethod.DELETE)
    public Boolean deleteRole(@RequestParam List<Long> ids){
        return platformRoleService.removeByIds(ids);
    }
}

