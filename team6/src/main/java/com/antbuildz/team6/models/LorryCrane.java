package com.antbuildz.team6.models;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
public class LorryCrane extends Transport{

    public LorryCrane(String serialNumber, Partner partner, double capacity, LocalDateTime listingDate){
        super(serialNumber, partner, capacity, listingDate);
    }

    public LorryCrane() {
    }

    @Override
    public Map<String, Object> getTransportDetails() {
        Map<String, Object> transportDetails = new HashMap<>();
        transportDetails.put("partner_email", super.getPartner().getEmail());
        transportDetails.put("serialNumber", super.getSerialNumber());
        transportDetails.put("capacity", super.getCapacity());
        transportDetails.put("listingDate", super.getListingDate());
        transportDetails.put("type", "LorryCrane");

        return transportDetails;
    }
}
