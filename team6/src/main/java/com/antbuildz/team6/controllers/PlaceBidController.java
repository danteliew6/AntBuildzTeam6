package com.antbuildz.team6.controllers;

import com.antbuildz.team6.models.Bid;
import com.antbuildz.team6.models.Partner;
import com.antbuildz.team6.models.Request;
import com.antbuildz.team6.repositories.BidRepository;
import com.antbuildz.team6.repositories.PartnerRepository;
import com.antbuildz.team6.repositories.RequestRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@CrossOrigin
@RestController
public class PlaceBidController {
    @Autowired
    BidRepository bidRepository;

    @Autowired
    PartnerRepository partnerRepository;

    @Autowired
    RequestRepository requestRepository;

    @PostMapping("/placebid")
    public Bid placeBid(@RequestBody String bidDetails) {
        // Test input
//        {
//            "request_id": 1,
//                "email": "hoho@gmail.com",
//                "price" : 70
//        }
        JSONObject jsonObject = new JSONObject(bidDetails);
        Optional<Partner> partner = partnerRepository.findById(jsonObject.getString("email"));
        Partner existingPartner = null;
        Request existingRequest = null;
        if (partner.isPresent()) {
            existingPartner = partner.get();
        }

        Optional<Request> request = requestRepository.findById(jsonObject.getInt("request_id"));
        if (request.isPresent()) {
            existingRequest = request.get();
        }

        double price = jsonObject.getDouble("price");
        if (existingPartner == null || existingRequest == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Request/Partner not found");
        }


        Bid bid = new Bid(existingRequest.getId(), existingPartner, price);

        bidRepository.save(bid);
        return bid;
    }


}
