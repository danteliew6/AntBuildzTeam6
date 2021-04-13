package com.antbuildz.team6.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name="user")
public class User {

    private String uenNumber;
    private String companyName;

    @Id
    private String email;
    private String password;
    private boolean isVerified;

    public User(String email, String password, String uenNumber, String companyName){
        this.email = email;
        this.password = password;
        this.uenNumber = uenNumber;
        this.companyName = companyName;
        this.isVerified = false;
    }

    public User() {
    }

    public String getUenNumber() {
        return uenNumber;
    }

    public void setUenNumber(String uenNumber) {
        this.uenNumber = uenNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public Map<String, Object> getUserDetails(){
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put( "email", email);
        return userDetails;
    }
}
