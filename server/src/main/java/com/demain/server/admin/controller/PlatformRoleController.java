package com.demain.server.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demain.framework.core.response.Result;
import com.demain.framework.web.page.PageRequest;
import com.demain.framework.web.page.PageResponse;
import com.demain.server.admin.entity.PlatformRole;
import com.demain.server.admin.service.PlatformRoleService;
import com.demain.framework.web.annotation.ResponseResult;
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
@ApiSupport(order = 20, author = "demain_lee")
@RestController
@RequestMapping("/admin/role")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PlatformRoleController {

    private final PlatformRoleService platformRoleService;

    @Operation(summary = "角色列表")
    @ApiOperationSupport(order = 1, author = "demain_lee")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<PlatformRole>> list() {
        return Result.data(platformRoleService.list());
    }

    @Operation(summary = "角色列表2")
    @ApiOperationSupport(order = 2, author = "demain_lee")
    @RequestMapping(value = "/list2", method = RequestMethod.GET)
    @ResponseResult
    public List<PlatformRole> list2() {
        return platformRoleService.list();
    }

    @Operation(summary = "角色列表3")
    @ApiOperationSupport(order = 3, author = "demain_lee")
    @RequestMapping(value = "/list3", method = RequestMethod.GET)
    @ResponseResult
    public Result<PageResponse<PlatformRole>> list3(PageRequest request) {
        Page<PlatformRole> page = new Page<>(request.getCurrent(),request.getSize());
        Page<PlatformRole> rolePage = platformRoleService.page(page);
        PageResponse<PlatformRole> pageResponse = PageResponse.<PlatformRole>builder()
                .current(rolePage.getCurrent())
                .size(rolePage.getSize())
                .total(rolePage.getTotal())
                .records(rolePage.getRecords()).build();
        return Result.data(pageResponse);
    }

    @Operation(summary = "添加角色")
    @ApiOperationSupport(order = 4, author = "demain_lee")
    @RequestMapping(value = "/saveRole", method = RequestMethod.POST)
    public Boolean saveRole(@RequestBody PlatformRole platformRole) {
        return platformRoleService.save(platformRole);
    }

    @Operation(summary = "编辑角色")
    @ApiOperationSupport(order = 5, author = "demain_lee")
    @RequestMapping(value = "/updateRole", method = RequestMethod.PUT)
    public Boolean updateRole(@RequestBody PlatformRole platformRole) {
        return platformRoleService.updateById(platformRole);
    }

    @Operation(summary = "删除角色")
    @ApiOperationSupport(order = 6, author = "demain_lee")
    @RequestMapping(value = "/deleteRole", method = RequestMethod.DELETE)
    public Boolean deleteRole(@RequestParam List<Long> ids) {
        return platformRoleService.removeByIds(ids);
    }
}

