package com.pawcie.authorization.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ApiController {

    @GetMapping("/public/hello")
    public String publicHello() {
        return "Hello user!";
    }

    @GetMapping("/private/hello")
    public String privateHello() {
        return "Hello " + SecurityContextHolder.getContext().getAuthentication().getName() + "!";
    }
}
