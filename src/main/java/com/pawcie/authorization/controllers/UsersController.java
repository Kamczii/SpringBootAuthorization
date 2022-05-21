package com.pawcie.authorization.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UsersController {

    @GetMapping("all")
    String[] printUsers() {
        return new String[]{"Kamil", "Paweł", "Kuba"};
    }

    @GetMapping("all2")
    String[] printUsers2() {
        return new String[]{"Kamil", "Paweł", "Kuba"};
    }

}
