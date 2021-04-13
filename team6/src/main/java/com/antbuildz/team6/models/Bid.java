package com.antbuildz.team6.models;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "bid")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer requestId;

    public Bid(Integer requestId, Partner partner, double price) {
        this.requestId = requestId;
        this.partner = partner;
        this.price = price;
    }

    public Bid() {
    }

    @ManyToOne
    private Partner partner;
    private double price;
    private Integer isSelected = 0; // 0 means not selected yet, 1 means selected, 2 means rejected

    @CreationTimestamp
    private Timestamp timestamp;

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Integer getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Integer isSelected) {
        this.isSelected = isSelected;
    }
}
