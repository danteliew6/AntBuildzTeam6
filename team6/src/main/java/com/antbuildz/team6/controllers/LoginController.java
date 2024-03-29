package com.antbuildz.team6.controllers;

import com.antbuildz.team6.models.Partner;
import com.antbuildz.team6.models.User;
import com.antbuildz.team6.repositories.BidRepository;
import com.antbuildz.team6.repositories.PartnerRepository;
import com.antbuildz.team6.repositories.RequestRepository;
import com.antbuildz.team6.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.json.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
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
        User newUser;
        try {
            if (jsonObject.getString("dtype").equals("User")) {
                newUser = new User(
                        jsonObject.getString("email"),
                        jsonObject.getString("password"),
                        jsonObject.getString("uenNumber"),
                        jsonObject.getString("companyName")
                );
                userRepository.save(newUser);
                return newUser;
            } else {
                newUser = new Partner(
                        jsonObject.getString("email"),
                        jsonObject.getString("password"),
                        jsonObject.getString("uenNumber"),
                        jsonObject.getString("companyName")
                );
                partnerRepository.save((Partner) newUser);
                return newUser;
            }
        }  catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Invalid user details"
            );
        }
    }


    @PostMapping("/validateuser")
    public String validateUser(@RequestBody String credentials) {
//        {
//            "email": "haha@gmail.com",
//            "password" : "1234567"
//        }
        // need to first establish the the person accessing this page is an admin.
        JSONObject jsonObject = new JSONObject(credentials);
        Optional<User> user = userRepository.findById(jsonObject.getString("email"));
        Optional<Partner> partnerOptional = partnerRepository.findById(jsonObject.getString("email"));

        if (partnerOptional.isPresent()) {
            Partner existingPartner = partnerOptional.get();
            if (existingPartner.getPassword().equals(jsonObject.getString("password")) && existingPartner.isVerified()) {
                return "partner";
            }
        } else if (user.isPresent()){
            User existingUser = user.get();
            if (existingUser.getPassword().equals(jsonObject.getString("password")) && existingUser.isVerified()) {
                return "user";
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User/Partner not found or not verified"
            );
        }


        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User/Partner not found");
    }


    @GetMapping("/users")
    public ArrayList<User> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        ArrayList<User> userList = new ArrayList<>();
        for (User user: users) userList.add(user);
        return userList;
    }

    @GetMapping("/getunverified")
    public ArrayList<User> getUnverifiedUsers(){
        ArrayList<User> users = userRepository.findAllUnverified();
        return users;
    }

    @PostMapping("/verifyuser")
    public boolean verifyUser(@RequestBody String jsonString){
        /*
        {
            "user_email":"haha@gmail.com"
        }
         */
        JSONObject jsonObject = new JSONObject(jsonString);
        return userRepository.verifyUser(jsonObject.getString("user_email")) > 0;
    }


}