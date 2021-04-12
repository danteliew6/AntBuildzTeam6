package com.antbuildz.team6.models;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name="request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    private User user;
    private LocalDateTime requestOpenDateTime;
    private int quantity;
    private String typeOfTransport; //this one not sure of data type
    private double capacityOfTransport;
    private String originLocation;
    private String destinationLocation;
    private LocalDateTime rentalStartDateTime;
    private LocalDateTime rentalEndDateTime; //optional
    private double equipmentVolume; //get volume of equipment user wants to transport
    private double equipmentWeight; // get weight of equipment. together with volume can calculate size of equipment
    private String specialRequest; //optional

    @OneToOne
    private Bid acceptedBid; //get the bid that was accepted

    public Request() {
    }

    public Request(User user, LocalDateTime requestOpenDateTime, int quantity, String typeOfTransport, double capacityOfTransport, String originLocation, String destinationLocation, LocalDateTime rentalStartDateTime, LocalDateTime rentalEndDateTime, double equipmentVolume, double equipmentWeight, String specialRequest) {
        this.user = user;
        this.requestOpenDateTime = requestOpenDateTime;
        this.quantity = quantity;
        this.typeOfTransport = typeOfTransport;
        this.capacityOfTransport = capacityOfTransport;
        this.originLocation = originLocation;
        this.destinationLocation = destinationLocation;
        this.rentalStartDateTime = rentalStartDateTime;
        this.rentalEndDateTime = rentalEndDateTime;
        this.equipmentVolume = equipmentVolume;
        this.equipmentWeight = equipmentWeight;
        this.specialRequest = specialRequest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getRequestOpenDateTime() {
        return requestOpenDateTime;
    }

    public void setRequestOpenDateTime(LocalDateTime requestOpenDateTime) {
        this.requestOpenDateTime = requestOpenDateTime;
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

    public double getCapacityOfTransport() {
        return capacityOfTransport;
    }

    public void setCapacityOfTransport(double capacityOfTransport) {
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

    public LocalDateTime getRentalStartDateTime() {
        return rentalStartDateTime;
    }

    public void setRentalStartDateTime(LocalDateTime rentalStartDateTime) {
        this.rentalStartDateTime = rentalStartDateTime;
    }

    public LocalDateTime getRentalEndDateTime() {
        return rentalEndDateTime;
    }

    public void setRentalEndDateTime(LocalDateTime rentalEndDateTime) {
        this.rentalEndDateTime = rentalEndDateTime;
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

    public Bid getAcceptedBid() {
        return acceptedBid;
    }

    public void setAcceptedBid(Bid acceptedBid) {
        this.acceptedBid = acceptedBid;
    }
}
