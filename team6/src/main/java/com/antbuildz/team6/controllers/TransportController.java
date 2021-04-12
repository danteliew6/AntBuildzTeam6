package com.antbuildz.team6.controllers;

import com.antbuildz.team6.models.LorryCrane;
import com.antbuildz.team6.models.Partner;
import com.antbuildz.team6.models.Transport;
import com.antbuildz.team6.repositories.PartnerRepository;
import com.antbuildz.team6.repositories.TransportRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TransportController {

    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @PostMapping("/addtransport")
    public Transport addTransport(@RequestBody String transportDetails){
        JSONObject jsonObject = new JSONObject(transportDetails);
        Optional<Partner> partner = partnerRepository.findById(jsonObject.getString("email"));
        if(!partner.isPresent()){
            return null;
        }
        if(jsonObject.getString("type").equals("Lorry Crane")){
            LorryCrane lc = new LorryCrane(partner.get(),Double.parseDouble(jsonObject.getString("capacity")));
            transportRepository.save(lc);
            return lc;
        }
        return null;
    }
}
