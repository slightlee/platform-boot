package com.demain.server.admin.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "登录管理")
@ApiSupport(order = 10, author = "demain_lee")
@Controller
public class LoginController {

    /**
     * 测试页面
     *
     * @return index
     */
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * 登录页面
     *
     * @return login
     */
    @RequestMapping("/loginPage")
    public String login() {
        return "login";
    }

}