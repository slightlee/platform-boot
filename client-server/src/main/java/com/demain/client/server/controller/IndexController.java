package com.demain.client.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class IndexController {
    
    Logger log = LoggerFactory.getLogger(IndexController.class);
    
    @GetMapping("/index2")
    public String index(Principal principal) {
        log.info("current login user: {}", principal.getName());
        return "index2页面: 您好," + principal.getName();
    }
    
}
