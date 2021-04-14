package com.antbuildz.team6.controllers;

import com.antbuildz.team6.models.Bid;
import com.antbuildz.team6.models.Request;
import com.antbuildz.team6.models.Transport;
import com.antbuildz.team6.models.User;
import com.antbuildz.team6.repositories.BidRepository;
import com.antbuildz.team6.repositories.RequestRepository;
import com.antbuildz.team6.repositories.TransportRepository;
import com.antbuildz.team6.repositories.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
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
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private TransportRepository transportRepository;

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
    public Map<String,Object> requestDetails(@PathVariable String requestId) {
        try {
            Optional<Request> optionalRequest = requestRepository.findById(Integer.parseInt(requestId));
            return optionalRequest.get().getRequestDetails();
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Invalid Request or Request ID"
            );
        }
    }


    @PostMapping("/selectbid")
    public boolean selectBid(@RequestBody String jsonString) {
        /*
        {
            "request_id":1,
            "bid_id":1
        }
         */

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
        existingBid.setIsSelected(1);
        bidRepository.save(existingBid);
        bidRepository.rejectRemainingBids(jsonObject.getInt("request_id"));
        return true;
    }

    @GetMapping("/viewrequest/{requestId}")
    public Map<String, Object> viewRequest(@PathVariable("requestId") Integer requestId) {
        // for user to see the top 3 bids and select the one they want

        Optional<Request> request = requestRepository.findById(requestId);

        if (!request.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Invalid Request or Request ID"
            );
        }

        Request existingRequest = request.get();

        ArrayList<Bid> bid = bidRepository.findByRequestId(requestId);

        // if there is already an accepted bid then the user should not be able to access this page.
        if(existingRequest.getAcceptedBid() != null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Invalid Request or Request ID"
            );
        }

        // return only the relevant details for the bid
        ArrayList<Map<String,Object>> bidDetails  = new ArrayList<>();
        for(Bid b: bid){
            bidDetails.add(b.getBidDetails());
        }

        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("request", existingRequest.getRequestDetails());
        jsonData.put("bids", bidDetails);

        return jsonData;
    }

    @GetMapping("/allopenrequests")
    public Map<Integer,Map<String, Object>> getAllOpenRequests(){
        ArrayList<Request> openRequests = requestRepository.findAllOpenRequests();
        Map<Integer,Map<String,Object>> jsonData = new HashMap<>();
        Integer i = 1;
        for (Request req : openRequests){
            jsonData.put(i++, req.getRequestDetails());
        }
        return jsonData;
    }

    // view the requests for a specifc user
    @PostMapping("/viewmyrequest")
    public Map<Integer,Object> viewUserRequest(@RequestBody String jsonString){
        /*
        {
            "user_email": "haha@gmail.com"
        }
         */
        Map<Integer,Object> requestDetails = new HashMap<>();
        JSONObject jsonObject = new JSONObject(jsonString);
        ArrayList<Request> myRequests = requestRepository.findOpenByEmail(jsonObject.getString("user_email"));
        Integer i = 1;
        for (Request req : myRequests){
            requestDetails.put(i++, req.getRequestDetails());
        }
        return requestDetails;
    }


    @GetMapping("/requestinvoice/{requestId}")
    public Map<String, Object> generateRequestInvoice(@PathVariable("requestId") Integer requestId) {
        Optional<Request> optionalRequest = requestRepository.findById(requestId);

        if (!optionalRequest.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Invalid Request or Request ID"
            );
        }

        Map<String, Object> requestDetails = optionalRequest.get().getRequestDetails();
        Bid acceptedBid = optionalRequest.get().getAcceptedBid();
        User user = (User)requestDetails.get("user");
        requestDetails.replace("user", user.getEmail());
        requestDetails.put("partner_email", acceptedBid.getPartner().getEmail());
        requestDetails.put("price", acceptedBid.getPrice());
        Optional<Transport> optionalTransport = transportRepository.findById(acceptedBid.getTransportSerialNumber());
        if (!optionalTransport.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No transport found"
            );
        }
        Transport transport = optionalTransport.get();
        requestDetails.put("transport_details", transport.getTransportDetails());

        return requestDetails;

    }

    @PostMapping("/getmyclosedrequests")
    public Map<Integer, Object> getUserClosedRequests(@RequestBody String userDetails){
        /*
        {
            "user_email":"haha@gmail.com"
        }
         */
        JSONObject jsonData = new JSONObject(userDetails);
        Map<Integer,Object> myClosedRequests = new HashMap<>();
        ArrayList<Request> myReq = requestRepository.findCloseByEmail(jsonData.getString("user_email"));
        int counter = 1;
        for(Request r : myReq){
            myClosedRequests.put(counter++, r.getRequestDetails());
        }
        return myClosedRequests;
    }
}
