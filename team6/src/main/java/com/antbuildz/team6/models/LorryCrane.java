package com.antbuildz.team6.models;

import javax.persistence.Entity;

@Entity
public class LorryCrane extends Transport{

    public LorryCrane(String serialNumber, Partner partner, double capacity){
        super(serialNumber, partner, capacity);
    }

    public LorryCrane() {
    }
}
