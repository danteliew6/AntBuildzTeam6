package com.antbuildz.team6.controllers;

import com.antbuildz.team6.models.Partner;
import com.antbuildz.team6.models.User;
import com.antbuildz.team6.repositories.BidRepository;
import com.antbuildz.team6.repositories.PartnerRepository;
import com.antbuildz.team6.repositories.RequestRepository;
import com.antbuildz.team6.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.json.*;
import org.springframework.web.server.ResponseStatusException;

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
    public User processSignUp(@RequestBody String userDetails) {
//        {
//            "uenNumber" : "01283945D",
//                "companyName" : "Your father company",
//                "email" : "hohohoho@gmail.com",
//                "password" : "12345678",
//                "verified" : true,
//                "dtype" : "Partner"
//        }
        JSONObject jsonObject = new JSONObject(userDetails);
        try {
            if (jsonObject.getString("dtype").equals("User")) {
                userRepository.save(new User(
                        jsonObject.getString("email"),
                        jsonObject.getString("password"),
                        jsonObject.getString("uenNumber"),
                        jsonObject.getString("companyName")
                ));
            } else {
                partnerRepository.save(new Partner(
                        jsonObject.getString("email"),
                        jsonObject.getString("password"),
                        jsonObject.getString("uenNumber"),
                        jsonObject.getString("companyName")
                ));
            }
        }  catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Invalid user details"
            );
        }

        return null;
    }


    @PostMapping("/validateuser")
    public String validateUser(@RequestBody String credentials) {
        // need to first establish the the person accessing this page is an admin.
        JSONObject jsonObject = new JSONObject(credentials);
        Optional<User> user = userRepository.findById(jsonObject.getString("email"));
        Optional<Partner> partnerOptional = partnerRepository.findById(jsonObject.getString("email"));

        if (user.isPresent()) {
            User existingUser = user.get();
            if (existingUser.getPassword().equals(jsonObject.getString("password"))) {
                return "user";
            }
        } else if (partnerOptional.isPresent()){
            Partner existingPartner = partnerOptional.get();
            if (existingPartner.getPassword().equals(jsonObject.getString("password"))) {
                return "partner";
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User/Partner not found"
            );
        }
        return null;
    }



}