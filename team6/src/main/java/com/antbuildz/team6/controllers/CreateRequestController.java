package com.antbuildz.team6.controllers;

import com.antbuildz.team6.models.Bid;
import com.antbuildz.team6.models.Request;
import com.antbuildz.team6.models.User;
import com.antbuildz.team6.repositories.BidRepository;
import com.antbuildz.team6.repositories.RequestRepository;
import com.antbuildz.team6.repositories.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
public class CreateRequestController {
//    {
//        "email": "haha@gmail.com",
//        ""
//    }
    @Autowired
    RequestRepository requestRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BidRepository bidRepository;

    // Request Creation
    // Home page -> new Request page (fill in form details) -> Form post to this route -> redirect back to home page
    @PostMapping("/addrequest")
    public String addRequest(@RequestBody String requestDetails) {
//        {
//            "email" : "hohoho@gmail.com",
//                "request_open_date_time": "2021-04-13 14:29",
//                "quantity" : 5,
//                "type_of_transport" : "LorryCrane",
//                "capacity_of_transport" : 100,
//                "origin_location" : "somewhere",
//                "destination_location" : "there",
//                "rental_start_date_time" : "2021-04-13 14:29",
//                "rental_end_date_time" : "2021-04-13 14:29",
//                "equipment_volume" : 100,
//                "equipment_weight" : 100,
//                "special_request" : "Something i guess"
//        }
        try {

            JSONObject jsonData = new JSONObject(requestDetails);
            Optional<User> user = userRepository.findById(jsonData.getString("email"));
            LocalDateTime requestOpenDateTime;
            LocalDateTime rentalStartDateTime;
            LocalDateTime rentalEndDateTime;
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                requestOpenDateTime  = LocalDateTime.parse(jsonData.getString("request_open_date_time"), formatter);
                rentalStartDateTime = LocalDateTime.parse(jsonData.getString("rental_start_date_time"), formatter);
                rentalEndDateTime = LocalDateTime.parse(jsonData.getString("rental_end_date_time"), formatter);

            } catch(Exception e){
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "Incorrect form details"
                );
            }

            requestRepository.save(new Request(
                    user.get(),
                    requestOpenDateTime,
                    jsonData.getInt("quantity"),
                    jsonData.getString("type_of_transport"),
                    jsonData.getDouble("capacity_of_transport"),
                    jsonData.getString("origin_location"),
                    jsonData.getString("destination_location"),
                    rentalStartDateTime,
                    rentalEndDateTime,
                    jsonData.getDouble("equipment_volume"),
                    jsonData.getDouble("equipment_weight"),
                    jsonData.getString("special_request")

            ));

            return "success";
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Incorrect form details"
            );
        }
    }

    // specific request detail for partner to view
    @GetMapping("/request/{requestId}")
    public Request requestDetails(@PathVariable String requestId) {
        try {
            Optional<Request> optionalRequest = requestRepository.findById(Integer.parseInt(requestId));
            return optionalRequest.get();
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Invalid Request or Request ID"
            );
        }
    }


    @PostMapping("/selectbid")
    public boolean selectBid(@RequestBody String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        Optional<Request> request = requestRepository.findById(jsonObject.getInt("request_id"));
        Optional<Bid> bid = bidRepository.findById(jsonObject.getInt("bid_id"));
        if (!request.isPresent() || !bid.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Invalid Request or Request ID"
            );
        }
        Bid existingBid = bid.get();
        Request existingRequest = request.get();

        if (existingRequest.getAcceptedBid() != null) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Request has already been filled"
            );
        }

        existingRequest.setAcceptedBid(existingBid);
        requestRepository.save(existingRequest);
        return true;
    }

    @GetMapping("/viewrequest/{request_id}")
    public Map<String, Object> viewRequest(@PathVariable("request_id") String requestId) {

        Optional<Request> request = requestRepository.findById(Integer.parseInt(requestId));

        if (!request.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Invalid Request or Request ID"
            );
        }

        Request existingRequest = request.get();

        ArrayList<Bid> bid = bidRepository.findByRequestId(Integer.parseInt(requestId));

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("request", existingRequest);
        jsonData.put("bids", bid);

        return jsonData;
    }

}
