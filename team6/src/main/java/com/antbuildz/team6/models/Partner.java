package com.antbuildz.team6.models;


import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class Partner extends User {
    private ArrayList<Transport> transports;

    public ArrayList<Transport> getTransports() {
        return transports;
    }

    public void setTransports(ArrayList<Transport> transports) {
        this.transports = transports;
    }
}
