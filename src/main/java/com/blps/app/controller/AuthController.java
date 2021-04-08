package com.blps.app.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequestMapping("/api/")
@RestController
public class AuthController {

    //login
    @CrossOrigin
    @PostMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
