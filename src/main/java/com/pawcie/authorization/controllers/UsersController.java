package com.pawcie.authorization.controllers;

import com.pawcie.authorization.entities.User;
import com.pawcie.authorization.services.UserService;
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
@Data
public class UsersController {
    @Autowired
    private final UserService userService;

    @GetMapping("/all")
    public List<User> printUsers() {
        return userService.getUsers();
    }

    //TODO avaliable all ids for admin

    @GetMapping(path = "{id}")
    public User getUser(@PathVariable("id") Integer userId, Principal principal){
        return userService.getUser(userId);
    }
}
