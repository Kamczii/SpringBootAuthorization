package com.pawcie.authorization.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        return "welcome";
    }
}
