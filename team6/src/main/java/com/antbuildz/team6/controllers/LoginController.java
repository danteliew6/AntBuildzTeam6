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



}