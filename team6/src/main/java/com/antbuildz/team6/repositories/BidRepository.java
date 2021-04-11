package com.antbuildz.team6.repositories;

import com.antbuildz.team6.models.Bid;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface BidRepository extends CrudRepository<Bid,Integer> {

    @Query("SELECT * FROM Request r WHERE user_id = LOWER(:email)")
    ArrayList<Bid> findByEmail(@Param("email") String email);
}
