package com.antbuildz.team6.controllers;

import com.antbuildz.team6.models.Request;
import com.antbuildz.team6.models.User;
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
import java.util.Optional;

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

    // Request Creation
    // Home page -> new Request page (fill in form details) -> Form post to this route -> redirect back to home page
    @PostMapping("/addrequest")
    public String addRequest(@RequestBody String requestDetails) {
//        {
//         "email" : "haha@gmail.com",
//            "request_open_date_time": "",
//            "quantity" : 5,
//            "type_of_transport" : "LorryCrane",
//            "capacity_of_transport" : 100,
//            "origin_location" : "somewhere",
//            "destination_location" : "there",
//            "rental_start_date_time" : "",
//            "rental_end_date_time" : "",
//            "equipment_volume" : 100,
//            "equipment_weight" : 100,
//            "special_request" : null
//        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm");
            JSONObject jsonData = new JSONObject(requestDetails);
            Optional<User> user = userRepository.findById(jsonData.getString("email"));
            LocalDateTime requestOpenDateTime = LocalDateTime.parse(jsonData.getString("request_open_date_time"), formatter);
            LocalDateTime rentalStartDateTime = LocalDateTime.parse(jsonData.getString("rental_start_date_time"), formatter);
            LocalDateTime rentalEndDateTime = LocalDateTime.parse(jsonData.getString("rental_end_date_time"), formatter);

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
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Incorrect form details"
            );
        }
        return "test";
    }

    // webpage of 1 specific request detail
    @GetMapping("/request/{id}")
    public String requestDetails(@PathVariable String requestId, Model model) {
        try {
            model.addAttribute("request", requestRepository.findById(Integer.parseInt(requestId)));
            return "request_confirmation"; // Page not done yet.
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Invalid Request or Request ID"
            );
        }
    }


}
