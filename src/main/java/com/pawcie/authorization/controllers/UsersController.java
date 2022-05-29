package com.pawcie.authorization.controllers;

import com.pawcie.authorization.entities.ApplicationUser;
import com.pawcie.authorization.services.ApplicationUserDetailsService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("users")
public class UsersController {

    @Autowired
    private ApplicationUserDetailsService userService;

    @GetMapping("/all")
    public String printUsers(Model model) {
        List<ApplicationUser> users = userService.loadUsers();
        model.addAttribute("users", users);
        return "users";
    }

    //TODO avaliable all ids for admin

    @GetMapping(path = "{id}")
    public String getUser(@PathVariable("id") Integer userId, Model model){
        ApplicationUser user = userService.loadUserById(userId);
        model.addAttribute("users", Collections.singleton(user));
        return "users";
    }
}
