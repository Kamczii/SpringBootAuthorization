package com.pawcie.authorization.controllers;

import com.pawcie.authorization.entities.ApplicationUser;
import com.pawcie.authorization.services.ApplicationUserDetailsService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    private ApplicationUserDetailsService userService;

    @GetMapping("/all")
    public List<ApplicationUser> printUsers() {
        return userService.loadUsers();
    }

    //TODO avaliable all ids for admin

    @GetMapping(path = "{id}")
    public ApplicationUser getUser(@PathVariable("id") Integer userId, Principal principal){
        return userService.loadUserById(userId);
    }
}
