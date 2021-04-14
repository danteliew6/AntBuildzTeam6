package com.antbuildz.team6.models;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "bid")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Request request;



    @ManyToOne
    private Partner partner;
    private double price;
    private Integer isSelected = 0; // 0 means not selected yet, 1 means selected, 2 means rejected
    private String transportSerialNumber;

    @CreationTimestamp
    private Timestamp timestamp;

    public Bid(Request request, Partner partner, double price, String transportSerialNumber) {
        this.request = request;
        this.partner = partner;
        this.price = price;
        this.transportSerialNumber = transportSerialNumber;
    }

    public Bid() {
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request requestId) {
        this.request = request;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTransportSerialNumber() {
        return transportSerialNumber;
    }

    public void setTransportSerialNumber(String transportSerialNumber) {
        this.transportSerialNumber = transportSerialNumber;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Integer getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Integer isSelected) {
        this.isSelected = isSelected;
    }

    public Map<String, Object> getBidDetails(){
        Map<String, Object> bidDetails = new HashMap<>();
        bidDetails.put("request",  request.getId());
        bidDetails.put("partner" , partner.getEmail());
        bidDetails.put("price" , price);
        bidDetails.put("id", id);
        return bidDetails;
    }
}
