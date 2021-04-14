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
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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


    @GetMapping("/")
    public String indexPage(Model model) {
        return "login";
    }

    @PostMapping("/index")
    public String loginPage(@ModelAttribute("credentials") User user, Model model){
        // Will send username and password over from the login fields
        // we will check igf it is user or partner
        // if it is a user then can only use userRepository
        // if it is a partner then can only use partnerRepository
        // we will do if else to see if either gives null. if both gives null means user does not exist
        // once we have the user or partner object, we call the respective repository and find all records of requests
        // or bids.
        // then we will use the model to send the data and populate the dashboard.
        // realised we cant send form data over as json, changed the data processing accordingly. can refer to login.html on how to construct the form data.
        // made a new constructor for user for this case


        Optional<User> userOptional = userRepository.findById(user.getEmail());
        Optional<Partner> partnerOptional = partnerRepository.findById(user.getEmail());
        User existingUser = null;
        Partner existingPartner = null;
        ArrayList<Request> requests = null;
        ArrayList<Bid> bids = null;
        if (!userOptional.isPresent() && !partnerOptional.isPresent()){
            // means this person is not valid, render login page;
            return "login";
        }
        else if (userOptional.isPresent()) {
            existingUser = userOptional.get();
            if (!existingUser.getPassword().equals(user.getPassword())) return "login";

            requests = requestRepository.findByEmail(existingUser.getEmail()); //NEED TO FIND HOW TO GET ALL REQUESTS BY USER ID

//            if (existingUser == null || requests == null) {
//                return null;
//            }

            // GO TO REQUEST TABLE AND FIND ALL THE REQUEST ID
            // THEN POPULATE THE MODEL WITH THE ARRAYLIST OF REQUESTS

            model.addAttribute("requests", requests);
            model.addAttribute("email", existingUser.getEmail());
            return "userHome";
        }
        else if (partnerOptional.isPresent()){
            existingPartner = partnerOptional.get();
            if (!existingPartner.getPassword().equals(user.getPassword())) return "login";
            bids = bidRepository.findByEmail(existingPartner.getEmail()); // NEED TO FIND HOW TO GET BIDS BY PARTNER ID


            if (existingPartner == null || bids == null) {
                return null;
            }

            // GO TO BIDS TABLE AND FIND ALL THE BIDS WITH THE REQUEST ID
            // THEN POPULATE THE MODEL WITH THE ARRAYLIST OF BIDS

            model.addAttribute("bids", bids);
            model.addAttribute("email", existingPartner.getEmail());
            return "partnerHome";
        } else {
            return "login";
        }
    }



    @GetMapping("/signup")
    public String signUpPage(Model model) {
        return "signup";
    }

    @GetMapping("/create_Request")
    public String createRequestPage(Model model){ return "createRequest";}

    @GetMapping("/userHome")
    public String userHomePage(Model model){ return "userHome";}

    @GetMapping("/partnerHome")
    public String partnerHomePage(Model model){ return "partnerHome";}

    @GetMapping("/result")
    public String resultPage(Model model) {
        return "result";
    }

    @GetMapping("/logout")
    public String logOutPage(Model model) {
        return "logout";
    }

    @GetMapping("/placeBid")
    public String placeBidPage(Model model) {
        return "placeBid";
    }

    @GetMapping("/selectBid")
    public String selectBidPage(Model model) {
        return "selectBid";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        return "admin";
    }

}