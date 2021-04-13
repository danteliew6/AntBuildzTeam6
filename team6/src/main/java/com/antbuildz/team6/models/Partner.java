package com.antbuildz.team6.models;


import com.antbuildz.team6.repositories.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Partner extends User {

    public Partner(String email, String password, String uenNumber, String companyName) {
        super(email, password, uenNumber, companyName);
    }

    public Partner() {
    }
    public Map<String,Object> getPartnerDetails(){
        return super.getUserDetails();
    }
}
