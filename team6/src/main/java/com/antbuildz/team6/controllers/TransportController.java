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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
            "capacity": "double"
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
                LorryCrane lc = new LorryCrane(jsonObject.getString("serial_number"), partner.get(), jsonObject.getDouble("capacity"));
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
}
