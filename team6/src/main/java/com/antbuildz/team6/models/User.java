package com.antbuildz.team6.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {
    @Id
    private String uenNumber;

    private String companyName;
    private String email;
    private String password;
    private boolean isVerified;

    public User() {
        this.companyName = "upstart";
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
}
