package com.demain.server.admin.controller;


import com.demain.framework.core.response.Result;
import com.demain.server.admin.entity.PlatformUser;
import com.demain.server.admin.service.PlatformUserService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author demain_lee
 * @since 2021-12-14
 */
@Tag(name = "用户接口")
@ApiSupport(order = 10, author = "demain_lee")
@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PlatformUserController {

    private final PlatformUserService platformUserService;


    @Operation(summary =  "用户列表")
    @ApiOperationSupport(order = 1, author = "demain_lee")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<PlatformUser> list() {
        return platformUserService.list();
    }

    @Operation(summary =  "用户列表2")
    @ApiOperationSupport(order = 2, author = "demain_lee")
    @RequestMapping(value = "/list2", method = RequestMethod.GET)
    public Result<List<PlatformUser>> list2() {
        return Result.data(platformUserService.list());
    }

    @Operation(summary =  "用户列表2")
    @ApiOperationSupport(order = 2, author = "demain_lee")
    @RequestMapping(value = "/list3", method = RequestMethod.GET)
    public Result list3() {
        return Result.success("操作成功");
    }

}


