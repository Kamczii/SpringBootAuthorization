package com.pawcie.authorization.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductsController {

    @GetMapping("all")
    String[] printProducts() {
        return new String[]{"shoe", "tee", "belt"};
    }
}
