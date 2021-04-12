package com.antbuildz.team6.models;

import javax.persistence.*;

@Entity
@Table(name="admin")
public class Admin {
    public Admin() {
    }

    @Id
    private String email;
    private String password;

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
}
