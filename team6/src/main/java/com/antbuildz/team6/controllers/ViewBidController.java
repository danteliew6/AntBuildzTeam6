package com.antbuildz.team6.controllers;

import com.antbuildz.team6.models.Bid;
import com.antbuildz.team6.models.Partner;
import com.antbuildz.team6.repositories.BidRepository;
import com.antbuildz.team6.repositories.PartnerRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
public class ViewBidController {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private PartnerRepository partnerRepository;

//    @GetMapping("/viewbid/{id}")
//    public String bidDetails(@PathVariable String bidId, Model model){
//        try {
//            model.addAttribute("bid", bidRepository.findById(Integer.parseInt(bidId)));
//            return "bid_detail"; // Page not done yet.
//        } catch (Exception e) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "Invalid Request or Request ID"
//            );
//        }
//    }

    @GetMapping("/viewbid/{bid_id}")
    public Map<String,Object> viewSpecificBid(@PathVariable("bid_id") Integer bidId){
        // view specific bid
        Optional<Bid> bid = bidRepository.findById(bidId);
        if(!bid.isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Bid not found");
        }
        Map<String,Object> bidDetails = new HashMap<>();
        bidDetails.put("request",bid.get().getRequest().getRequestDetails());
        bidDetails.put("bid",bid.get().getBidDetails());
        return bidDetails;

    }

    @PostMapping("/viewmybids")
    public Map<String, Object> viewPartnerBids(@RequestBody String partnerDetails){
        // view all bids that this partner has placed
        /*
        {
            "partner_email" : "hoho@gmail.com
        }
         */
        JSONObject jsonObject = new JSONObject(partnerDetails);
        Optional<Partner> partner = partnerRepository.findById(jsonObject.getString("partner_email"));
        if(!partner.isPresent()){
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Partner not found");
        }
        ArrayList<Bid> bids = bidRepository.findByEmail(partner.get().getEmail());
        Map<Integer, Map<String,Object>> bidDetails = new HashMap<>();
        Map<String,Object> bidOutDetails = new HashMap<>();
        for(Bid b : bids){
            bidDetails.put(b.getId(), b.getBidDetails());
        }
        bidOutDetails.put("bids",bidDetails);
        return bidOutDetails;
    }

    @PostMapping("/viewmyacceptedbids")
    public Map<Integer,Object> viewMyAcceptedBids(@RequestBody String partnerDetails){
        /*
        {
            "partner_email":"hoho@gmail.com"
        }
         */
        Map<Integer, Object> acceptedBids = new HashMap<>();
        JSONObject jsonObject = new JSONObject(partnerDetails);
        ArrayList<Bid> myAcceptedBids = bidRepository.findMyAcceptedBids(jsonObject.getString("partner_email"));
        int counter = 0;
        for(Bid b : myAcceptedBids){
            acceptedBids.put(counter++, b.getBidDetails());
        }
        return acceptedBids;
    }
}
