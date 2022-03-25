package com.demain.platform.admin.controller;


import com.demain.platform.admin.entity.PlatformUser;
import com.demain.platform.admin.service.PlatformUserService;
import com.demain.platform.core.annotation.ResponseResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @author 明天
 * @since 2021-12-14
 */
@Api(value = "用户接口", tags = "用户" )
@ApiSupport(order = 10,author = "明天")
@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PlatformUserController {

    private final PlatformUserService platformUserService;


    @ApiOperation(value = "用户列表")
    @ApiOperationSupport(order = 1,author = "明天")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseResult
    public List<PlatformUser> list(){
        return platformUserService.list();
    }

    @ApiOperation(value = "用户列表2")
    @ApiOperationSupport(order = 2,author = "明天")
    @RequestMapping(value = "/list2",method = RequestMethod.GET)
    @ResponseResult
    public List<PlatformUser> list2(){
        return platformUserService.list();
    }

    @ApiOperation(value = "用户列表3")
    @ApiOperationSupport(order = 3,author = "明天")
    @RequestMapping(value = "/list3",method = RequestMethod.GET)
    @ResponseResult
    public List<PlatformUser> list3(){
        return platformUserService.list();
    }
}

