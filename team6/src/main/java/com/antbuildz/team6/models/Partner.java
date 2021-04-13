package com.antbuildz.team6.models;


import com.antbuildz.team6.repositories.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Partner extends User {

    @Autowired
    TransportRepository transportRepository;

    public Partner(String email, String password, String uenNumber, String companyName) {
        super(email, password, uenNumber, companyName);
    }

    public Partner() {
    }

    public Map<String,Object> getPartnerDetails(){
        Map<String,Object> partnerDetails = super.getUserDetails();
        ArrayList<String> transportSerialNums = new ArrayList<>();
        for(Transport t : transportRepository.findPartnerTransports(this.getEmail())){
            transportSerialNums.add(t.getSerialNumber());
        }
        partnerDetails.put("transports",transportSerialNums);
        return partnerDetails;
    }
}
