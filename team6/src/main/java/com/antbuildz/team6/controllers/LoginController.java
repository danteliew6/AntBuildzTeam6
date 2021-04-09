package com.antbuildz.team6.controllers;

import com.antbuildz.team6.repositories.PartnerRepository;
import com.antbuildz.team6.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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