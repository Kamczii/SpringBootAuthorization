package com.pawcie.authorization.controllers;

import com.pawcie.authorization.entities.Contact;
import com.pawcie.authorization.services.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="contact")
@AllArgsConstructor
public class ContactController {

    @Autowired
    private final ContactService contactService;

    @GetMapping("all")
    public Contact getContact(){
        return contactService.getContact();
    }

}
