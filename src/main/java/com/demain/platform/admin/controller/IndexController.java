package com.demain.platform.admin.controller;

import com.demain.platform.core.annotation.PreventDuplication;
import com.demain.platform.core.annotation.ResponseResult;
import com.demain.platform.core.util.Result;
import com.demain.platform.core.util.ResultEnum;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Api(value = "Demo接口", tags = "Demo" )
@ApiSupport(order = 10,author = "明天")
@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class IndexController {


    @ApiOperation(value = "demo1")
    @ApiOperationSupport(order = 1,author = "明天")
    @RequestMapping(value = "/demo1",method = RequestMethod.GET)
    @ResponseResult
    public List list(){
        ArrayList<Object> list = new ArrayList<>();
        return list;
    }

    @ApiOperation(value = "demo2")
    @ApiOperationSupport(order = 2,author = "明天")
    @RequestMapping(value = "/demo2",method = RequestMethod.GET)
    public Result<String> demo2(){
        return Result.data("123456");
    }

    @ApiOperation(value = "demo3")
    @ApiOperationSupport(order = 3,author = "明天")
    @RequestMapping(value = "/demo3",method = RequestMethod.POST)
    public Result save(){
        return true ? Result.success() : Result.success(ResultEnum.ADD_FAIL) ;
    }


    @ApiOperation(value = "demo4")
    @ApiOperationSupport(order = 4,author = "明天")
    @RequestMapping(value = "/demo4",method = RequestMethod.POST)
    public Result demo3(){
        return  Result.success("操作成功11111");
    }


    @PreventDuplication
    @ApiOperation(value = "测试重复提交")
    @ApiOperationSupport(order = 5,author = "明天")
    @RequestMapping(value = "/repeatSubmit",method = RequestMethod.POST)
    public Result repeatSubmit(HttpServletRequest request){
        log.info("提交了....");
        return  Result.success();
    }


}
