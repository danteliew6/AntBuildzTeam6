package com.antbuildz.antbuildz.controllers;

import com.antbuildz.antbuildz.repositories.PartnerRepository;
import com.antbuildz.antbuildz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {
    //The repositories to check if the login credentials are right
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @GetMapping("/springz")
    public String loginPage(){
        //return the webpage for login
        return "hahaha";
    }

    @GetMapping("/springer")
    public String userLogin(){
        //do authentication of user then if it is correct, redirect to the dashboard page, else validation message and remain at same login page
        return "weimin nerd";
    }

}
