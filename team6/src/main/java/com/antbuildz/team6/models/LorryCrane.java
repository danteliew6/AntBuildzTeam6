package com.antbuildz.team6.models;

public class LorryCrane extends Transport{
    private double capacity;

    public LorryCrane(Partner partner, double capacity){
        super(partner);
        this.capacity = capacity;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }
}
