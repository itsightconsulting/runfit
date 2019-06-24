package com.itsight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsight.service.FacebookUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class FacebookUserController {

    private FacebookUserService facebookUserService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public FacebookUserController(FacebookUserService facebookUserService) {
        this.facebookUserService = facebookUserService;
    }


    @GetMapping
    public Principal getUser(Principal user) {

       System.out.println(user);


        return user;
    }
}
