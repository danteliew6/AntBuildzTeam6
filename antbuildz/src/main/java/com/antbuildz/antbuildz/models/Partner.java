package com.antbuildz.antbuildz.models;

import javax.persistence.Entity;
import java.util.ArrayList;

<<<<<<< HEAD:team6/src/main/java/com/antbuilds/team6/data/Partner.java

=======
@Entity
>>>>>>> b033625fdd6492a8f21e85ac1f6adc5023157c88:antbuildz/src/main/java/com/antbuildz/antbuildz/models/Partner.java
public class Partner extends User {
    private ArrayList<Transport> transports;

    public ArrayList<Transport> getTransports() {
        return transports;
    }

    public void setTransports(ArrayList<Transport> transports) {
        this.transports = transports;
    }
}
