package com.antbuildz.team6.models;

import javax.persistence.*;

@Entity
@Table(name = "transport")
public class Transport {
    @Id
    private String serialNumber;

    @OneToOne
    private Partner partner;

    private double capacity;

    public Transport(String serialNumber, Partner partner, double capacity) {
        this.serialNumber = serialNumber;
        this.partner = partner;
        this.capacity = capacity;
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


}
