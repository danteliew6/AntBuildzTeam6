package com.antbuildz.team6.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transport")
public class Transport {
    @Id
    private String serialNumber;

    @OneToOne
    private Partner partner;

    private double capacity;
    private LocalDateTime listingDate;

    public Transport(String serialNumber, Partner partner, double capacity, LocalDateTime listingDate) {
        this.serialNumber = serialNumber;
        this.partner = partner;
        this.capacity = capacity;
        this.listingDate = listingDate;
    }

    public Transport() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Partner getPartner() {
        return partner;
    }

    public double getCapacity() {return capacity;}

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public void setSerialNumber(Integer id) {
        this.serialNumber = serialNumber;
    }

    public void setCapacity() {this.capacity = capacity;}

    public LocalDateTime getListingDate() {
        return listingDate;
    }

    public void setListingDate(LocalDateTime listingDate) {
        this.listingDate = listingDate;
    }
}
