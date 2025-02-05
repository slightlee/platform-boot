package com.demain.authorization.server.web;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class LoginController {
    
    Logger log = LoggerFactory.getLogger(LoginController.class);
    
    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        Object attribute = session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (attribute instanceof AuthenticationException exception) {
            model.addAttribute("error", exception.getMessage());
        }
        return "login";
    }
    
    @GetMapping("/index")
    @ResponseBody
    public String index(Principal principal) {
        log.info("current login user: {}", principal.getName());
        return "您好," + principal.getName();
    }
}
