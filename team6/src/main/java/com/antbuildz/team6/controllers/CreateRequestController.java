package com.antbuildz.team6.controllers;

import com.antbuildz.team6.models.Request;
import com.antbuildz.team6.repositories.RequestRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CreateRequestController {

    @Autowired
    RequestRepository requestRepository;

    // Request Creation
    // Home page -> new Request page (fill in form details) -> Form post to this route -> redirect back to home page
    @PostMapping("/addrequest")
    public String addRequest(@RequestBody String requestDetails) {
        try {
            JSONObject jsonData = new JSONObject(requestDetails);


            requestRepository.save(requestDetails);
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
