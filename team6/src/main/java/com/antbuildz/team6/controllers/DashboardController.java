package com.antbuildz.team6.controllers;

import com.antbuildz.team6.models.Bid;
import com.antbuildz.team6.models.Partner;
import com.antbuildz.team6.models.Request;
import com.antbuildz.team6.models.User;
import com.antbuildz.team6.repositories.BidRepository;
import com.antbuildz.team6.repositories.PartnerRepository;
import com.antbuildz.team6.repositories.RequestRepository;
import com.antbuildz.team6.repositories.UserRepository;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private RequestRepository requestRepository;




    @GetMapping("/index")
    public String loginPage(@RequestBody String credentials, Model model){
        // Will send username and password over from the login fields
        // we will check igf it is user or partner
        // if it is a user then can only use userRepository
        // if it is a partner then can only use partnerRepositroy
        // we will do if else to see if either gives null. if both gives null means user does not exist
        // once we have the user or partner object, we call the respective repository and find all records of requests
        // or bids.
        // then we will use the model to send the data and populate the dashboard.
        JSONObject jsonObject = new JSONObject(credentials);
        Optional<User> userOptional = userRepository.findById(jsonObject.getString("email"));
        Optional<Partner> partnerOptional = partnerRepository.findById(jsonObject.getString("email"));
        User existingUser = null;
        Partner existingPartner = null;
        Request existingRequest = null;
        Bid existingBid = null;
        if (!userOptional.isPresent() && !partnerOptional.isPresent()){
            // means this person is not valid, render login page;
            return "login";
        }
        else if (userOptional.isPresent()) {
            existingUser = userOptional.get();
            if (!existingUser.getPassword().equals(jsonObject.getString("password"))) return "login";
            Optional<Request> request = requestRepository.findById(jsonObject.getInt("request_id")); //NEED TO FIND HOW TO GET ALL REQUESTS BY USER ID
            if (request.isPresent()) {
                existingRequest = request.get();
            }

            double price = jsonObject.getDouble("price");
            if (existingUser == null || existingRequest == null) {
                return null;
            }

            // GO TO REQUEST TABLE AND FIND ALL THE REQUEST ID
            // THEN POPULATE THE MODEL WITH THE ARRAYLIST OF REQUESTS

            //model.addAttribute("Requests", ArrayList<Request> requests)
            return "userHome";
        }
        else if (partnerOptional.isPresent()){
            existingPartner = partnerOptional.get();
            if (!existingPartner.getPassword().equals(jsonObject.getString("password"))) return "login";
            Optional<Bid> bids = bidRepository.findById(jsonObject.getInt(existingPartner.getEmail())); // NEED TO FIND HOW TO GET BIDS BY PARTNER ID
            if (bids.isPresent()) {
                existingBid = bids.get();
            }

            double price = jsonObject.getDouble("price");
            if (existingPartner == null || existingBid == null) {
                return null;
            }

            // GO TO BIDS TABLE AND FIND ALL THE BIDS WITH THE REQUEST ID
            // THEN POPULATE THE MODEL WITH THE ARRAYLIST OF BIDS

            //model.addAttribute("Bids", ArrayList<Bid> bids)
            return "partnerHome";
        }



        model.addAttribute("name", "your mother");
        return "login";
    }



    @GetMapping("/signup")
    public String signUpPage(Model model) {
        return "signup";
    }

//    @PostMapping("/getBids")
//    public ArrayList<Bid> getBids(@RequestBody Partner user){
//        // get the user object and then find and return the bids associated with them
//        JSONObject jsonObject = new JSONObject(user);
//        Optional<User> userOptional = userRepository.findById(jsonObject.getString("email"));
//        Optional<Partner> partnerOptional = partnerRepository.findById(jsonObject.getString("email"));
//        User existingUser = null;
//        Request existingRequest = null;
//        if (!userOptional.isPresent() && !partnerOptional.isPresent()){
//            // means this person is not valid, render login page;
//            return "login";
//        }
//        else if (userOptional.isPresent()) {
//            existingUser = userOptional.get();
//        }
//
//        Optional<Request> request = requestRepository.findById(jsonObject.getInt("request_id"));
//        if (request.isPresent()) {
//            existingRequest = request.get();
//        }
//
//        double price = jsonObject.getDouble("price");
//        if (existingPartner == null || existingRequest == null) {
//            return null;
//        }
//
//
//        Bid bid = new Bid(existingRequest, existingPartner, price);
////        existingRequest.setQuantity(5);
////        requestRepository.save(existingRequest);
//        bidRepository.save(bid);
//        return bid;
//    }

//    @PostMapping("/getRequest")
//    public ArrayList<Request> getRequests(@RequestBody User user){
//
//    }


}