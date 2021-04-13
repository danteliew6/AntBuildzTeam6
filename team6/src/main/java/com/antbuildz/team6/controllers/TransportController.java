package com.antbuildz.team6.controllers;

import com.antbuildz.team6.models.LorryCrane;
import com.antbuildz.team6.models.Partner;
import com.antbuildz.team6.models.Transport;
import com.antbuildz.team6.repositories.LorryCraneRepository;
import com.antbuildz.team6.repositories.PartnerRepository;
import com.antbuildz.team6.repositories.TransportRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
public class TransportController {

    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private LorryCraneRepository lorryCraneRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @PostMapping("/addtransport")
    public Transport addTransport(@RequestBody String transportDetails){
        /*
        {   "type": "Lorry Crane",
            "serial_number": "serialNumber",
            "partner_email": "email@email.com",
            "capacity": "double",
            "listing_date" : "datetime"
        }
         */

        JSONObject jsonObject = new JSONObject(transportDetails);
        try {
            Optional<Partner> partner = partnerRepository.findById(jsonObject.getString("partner_email"));
            if (!partner.isPresent()) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "Invalid Transport Details"
                );
            }
            if (jsonObject.getString("type").equals("Lorry Crane")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime listingDate  = LocalDateTime.parse(jsonObject.getString("listing_date"), formatter);
                LorryCrane lc = new LorryCrane(jsonObject.getString("serial_number"), partner.get(), jsonObject.getDouble("capacity"),listingDate);
                lorryCraneRepository.save(lc);
                return lc;
            }
            return null;
        } catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Invalid Transport Details"
            );
        }
    }

    @PostMapping("/gettransport")
    public Map<String,Object> getPartnerTransport(@RequestBody String partnerInfo){
        // this will return the transports that the partner has. so you can use this to populate the drop down list when the partner
        // decides which transport to put into the bid
        /*
        {
            "partner_email" : "haha@gmail.com",
            "transport_type" : "Lorry Crane"
        }
         */

        JSONObject jsonObject = new JSONObject(partnerInfo);
        try{
            Map<String,Object> partnerTransports = new HashMap<>();
            ArrayList<Transport> transports = transportRepository.findPartnerTransports(jsonObject.getString("partner_email"));
            partnerTransports.put(jsonObject.getString("transport_type"),transports);
            return partnerTransports;
        } catch (Exception e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Invalid Partner Details"
            );
        }

    }

    @GetMapping("/deletetransport/{serialNum}")
    public boolean deleteTransport(@PathVariable("serialNum") String serialNum){
        //delets the transport
        int rows = transportRepository.deleteTransport(serialNum);
        return rows > 0;
    }
}
