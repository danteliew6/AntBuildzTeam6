package com.antbuildz.team6.repositories;

import com.antbuildz.team6.models.Transport;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.ArrayList;

public interface TransportRepository extends CrudRepository<Transport,String> {

    @Query("SELECT t FROM Transport t WHERE partner_email = :email")
    ArrayList<Transport> findPartnerTransports(@Param("email") String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Transport t WHERE serial_number = :serial_number")
    int deleteTransport(@Param("serial_number") String serialNum);


}
