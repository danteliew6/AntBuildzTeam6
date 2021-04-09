package com.antbuildz.team6.controllers;

import com.antbuildz.team6.models.User;
import com.antbuildz.team6.repositories.PartnerRepository;
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

            JSONObject jsonObject = new JSONObject(credentials);
            Optional<User> user = userRepository.findById(jsonObject.getString("email"));
            if (user.isPresent()){
                User existingUser = user.get();
                if (existingUser.getPassword().equals(jsonObject.getString("password"))) {
                    return true;
                }
            }
            return false;
        }
}