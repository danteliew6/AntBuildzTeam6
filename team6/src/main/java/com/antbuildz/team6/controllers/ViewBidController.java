package com.antbuildz.team6.controllers;

import com.antbuildz.team6.models.Bid;
import com.antbuildz.team6.repositories.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@CrossOrigin
@RestController
public class ViewBidController {

    @Autowired
    private BidRepository bidRepository;

    @GetMapping("/viewbid/{id}")
    public String bidDetails(@PathVariable String bidId, Model model){
        try {
            model.addAttribute("bid", bidRepository.findById(Integer.parseInt(bidId)));
            return "bid_detail"; // Page not done yet.
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Invalid Request or Request ID"
            );
        }
    }
}
