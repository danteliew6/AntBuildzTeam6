package com.antbuildz.team6.controllers;

import com.antbuildz.team6.models.User;
import com.antbuildz.team6.repositories.BidRepository;
import com.antbuildz.team6.repositories.PartnerRepository;
import com.antbuildz.team6.repositories.RequestRepository;
import com.antbuildz.team6.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.json.*;

import java.util.Optional;

@RestController
public class LoginController {
    //The repositories to check if the login credentials are right
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private RequestRepository requestRepository;

//    @GetMapping("/springz")
//    public String loginPage(){
//        //return the webpage for login
//        return "hahaha";
//    }
//
//    @GetMapping("/springer")
//    public String userLogin(){
//        //do authentication of user then if it is correct, redirect to the dashboard page, else validation message and remain at same login page
//        return "weimin nerd";
//    }

    @PostMapping("/signup")
    public User processSignUp(@RequestBody User user) {
//        {
//            "uenNumber" : "01283945D",
//                "companyName" : "Your father company",
//                "email" : "hohohoho@gmail.com",
//                "password" : "12345678",
//                "verified" : true,
//                "dtype" : "Partner"
//        }
        userRepository.save(user);
        return user;
    }

//    @GetMapping("/login/{username}")
//    public User validateUser(@PathVariable String username) {
//        Optional<User> user = userRepository.findById(username);
//        if (user.isPresent()){
//            return user.get();
//        }
//    }

    @PostMapping("/validateuser")
    public boolean validateUser(@RequestBody String credentials) {
        // need to first establish the the person accessing this page is an admin.
        JSONObject jsonObject = new JSONObject(credentials);
        Optional<User> user = userRepository.findById(jsonObject.getString("email"));
        if (user.isPresent()) {
            User existingUser = user.get();
            if (existingUser.getPassword().equals(jsonObject.getString("password"))) {
                return true;
            }
        }
        return false;
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody String credentials) {
        // NOT USING ANYMORE
        // check the user details and establish the following
        // 1. Is it a partner or a user
        // 2. Send the relevant details for the respective classes
        JSONObject jsonObject = new JSONObject(credentials);
        Optional<User> userOptional = userRepository.findById(jsonObject.getString("email"));
        // check if the userRepository returns you an object of partner subclass. then we can just check instanceof
        // if not we can do an if else using userRepository and partnerRepository.
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            if (existingUser.getPassword().equals(jsonObject.getString("password"))) {
                // LEARN HOW TO ACCESS DTYPE FROM THE USER TABLE TO DETERMINE IF THIS GUY IS USER OR PARTNER
                // once establish the type, then we will send the user type (User or Partner)
                // {
                //      "flag": User or Partner,
                //  }
                return null;
            }
        }
        return null;

    }

}