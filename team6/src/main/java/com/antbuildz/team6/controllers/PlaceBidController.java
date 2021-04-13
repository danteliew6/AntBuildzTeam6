package com.antbuildz.team6.controllers;

import com.antbuildz.team6.models.Bid;
import com.antbuildz.team6.models.Partner;
import com.antbuildz.team6.models.Request;
import com.antbuildz.team6.models.Transport;
import com.antbuildz.team6.repositories.BidRepository;
import com.antbuildz.team6.repositories.PartnerRepository;
import com.antbuildz.team6.repositories.RequestRepository;
import com.antbuildz.team6.repositories.TransportRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
public class PlaceBidController {
    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private TransportRepository transportRepository;

    @PostMapping("/placebid")
    public Map<String,Object> placeBid(@RequestBody String bidDetails) {
        // Test input
//        {
//            "request_id": 1,
//            "email": "hoho@gmail.com",
//            "price" : 70,
//            "transport_serial_num" : "123",
//            "bid_id" : 1 <optional>
//        }

        JSONObject jsonObject = new JSONObject(bidDetails);

        Optional<Partner> partner = partnerRepository.findById(jsonObject.getString("email"));
        Partner existingPartner = null;
        Request existingRequest = null;
        Transport existingTransport = null;
        if (partner.isPresent()) {
            existingPartner = partner.get();
        }

        Optional<Request> request = requestRepository.findById(jsonObject.getInt("request_id"));
        if (request.isPresent() && request.get().getAcceptedBid() == null) {
            existingRequest = request.get();
        }

        Optional<Transport> transport = transportRepository.findById(jsonObject.getString("transport_serial_num"));
        if (transport.isPresent()){
            existingTransport = transport.get();
        }

        double price = jsonObject.getDouble("price");
        if (existingPartner == null || existingRequest == null || existingTransport == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Request/Partner/Transport not found or Request has been closed");
        }

        Bid bid;
        if(jsonObject.has("bid_id")){
          bid = bidRepository.findById(jsonObject.getInt("bid_id")).get();
          bid.setPrice(price);
          bid.setTransportSerialNumber(existingTransport.getSerialNumber());
        }  else {
            bid = new Bid(existingRequest, existingPartner, price, existingTransport.getSerialNumber());
        }

        bidRepository.save(bid);
        return bid.getBidDetails();
    }







}
