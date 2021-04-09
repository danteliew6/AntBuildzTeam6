package com.antbuildz.antbuildz.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name="request")
public class Request {
    @Id
    private int id;

    @OneToOne
    private User user;

    private Date requestOpenDateTime;
    private Date requestCloseDateTime;
    private int quantity;
    private String typeOfTransport; //this one not sure of data type
    private String capacityOfTransport;
    private String originLocation;
    private String destinationLocation;
    private Date rentalStartDateTime;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bid getAcceptedBid() {
        return acceptedBid;
    }

    public void setAcceptedBid(Bid acceptedBid) {
        this.acceptedBid = acceptedBid;
    }

    private Date rentalEndDateTime; //optional

    @OneToOne
    private Bid acceptedBid; //get the bid that was accepted


    private ArrayList<Bid> bidsPlaced; // all bids that were placed for this request
    private double equipmentVolume; //get volume of equipment user wants to transport
    private double equipmentWeight; // get weight of equipment. together with volume can calculate size of equipment
    private String specialRequest; //optional

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRequestOpenDateTime() {
        return requestOpenDateTime;
    }

    public void setRequestOpenDateTime(Date requestOpenDateTime) {
        this.requestOpenDateTime = requestOpenDateTime;
    }

    public Date getRequestCloseDateTime() {
        return requestCloseDateTime;
    }

    public void setRequestCloseDateTime(Date requestCloseDateTime) {
        this.requestCloseDateTime = requestCloseDateTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTypeOfTransport() {
        return typeOfTransport;
    }

    public void setTypeOfTransport(String typeOfTransport) {
        this.typeOfTransport = typeOfTransport;
    }

    public String getCapacityOfTransport() {
        return capacityOfTransport;
    }

    public void setCapacityOfTransport(String capacityOfTransport) {
        this.capacityOfTransport = capacityOfTransport;
    }

    public String getOriginLocation() {
        return originLocation;
    }

    public void setOriginLocation(String originLocation) {
        this.originLocation = originLocation;
    }

    public String getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(String destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public Date getRentalStartDateTime() {
        return rentalStartDateTime;
    }

    public void setRentalStartDateTime(Date rentalStartDateTime) {
        this.rentalStartDateTime = rentalStartDateTime;
    }

    public Date getRentalEndDateTime() {
        return rentalEndDateTime;
    }

    public void setRentalEndDateTime(Date rentalEndDateTime) {
        this.rentalEndDateTime = rentalEndDateTime;
    }

    public ArrayList<Bid> getBidsPlaced() {
        return bidsPlaced;
    }

    public void setBidsPlaced(ArrayList<Bid> bidsPlaced) {
        this.bidsPlaced = bidsPlaced;
    }

    public double getEquipmentVolume() {
        return equipmentVolume;
    }

    public void setEquipmentVolume(double equipmentVolume) {
        this.equipmentVolume = equipmentVolume;
    }

    public double getEquipmentWeight() {
        return equipmentWeight;
    }

    public void setEquipmentWeight(double equipmentWeight) {
        this.equipmentWeight = equipmentWeight;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }
}
