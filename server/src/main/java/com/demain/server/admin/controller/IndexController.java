package com.demain.server.admin.controller;

import com.demain.framework.core.exception.PlatformException;
import com.demain.framework.core.response.Result;
import com.demain.framework.web.annotation.ResponseResult;
import com.demain.framework.web.util.IPUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Demo", description = "Demo接口")
@ApiSupport(order = 10, author = "demain_lee")
@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class IndexController {


    @Operation(summary = "demo1")
    @ApiOperationSupport(order = 1, author = "demain_lee")
    @RequestMapping(value = "/demo1", method = RequestMethod.GET)
    public List list(HttpServletRequest request) {
        ArrayList<Object> list = new ArrayList<>();
        log.info("ip地址为：{}", IPUtil.getIpAddr(request));
        return list;
    }

    @Operation(summary = "demo2")
    @ApiOperationSupport(order = 2, author = "demain_lee")
    @RequestMapping(value = "/demo2", method = RequestMethod.GET)
    public Result<String> demo2() {
        try {
            int count = 1 / 0;
        } catch (Exception e) {
            throw new PlatformException(e.getMessage());
        }
        return Result.data("123456");
    }

    @Operation(summary = "demo3")
    @ApiOperationSupport(order = 3, author = "demain_lee")
    @RequestMapping(value = "/demo3", method = RequestMethod.GET)
    public Result<String> demo3() {
        int count = 1 / 0;
        return Result.data("123456");
    }


    @Operation(summary = "demo4")
    @ApiOperationSupport(order = 4, author = "demain_lee")
    @RequestMapping(value = "/demo4", method = RequestMethod.GET)
    public Result demo4() {
        return Result.success("操作成功11111");
    }

    @Operation(summary = "demo5")
    @ApiOperationSupport(order = 5, author = "demain_lee")
    @RequestMapping(value = "/demo5", method = RequestMethod.GET)
    @ResponseResult
    public String demo5() {
        return "操作成功11111";
    }


}
