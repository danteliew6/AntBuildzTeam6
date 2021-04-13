package com.antbuildz.team6.models;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class LorryCrane extends Transport{

    public LorryCrane(String serialNumber, Partner partner, double capacity, LocalDateTime listingDate){
        super(serialNumber, partner, capacity, listingDate);
    }

    public LorryCrane() {
    }
}
