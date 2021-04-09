package com.antbuildz.antbuildz.models;

import javax.persistence.*;

@Entity
@Table(name = "transport")
public class Transport {
    @Id
    private Integer id;

    @OneToOne
    private Partner partner;

    public Integer getId() {
        return id;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
