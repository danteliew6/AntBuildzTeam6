package com.antbuildz.team6.repositories;

import com.antbuildz.team6.models.Bid;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface BidRepository extends CrudRepository<Bid,Integer> {

    @Query("SELECT b FROM Bid b WHERE partner_email = LOWER(:email)")
    ArrayList<Bid> findByEmail(@Param("email") String email);

    @Query(nativeQuery = true, value = "SELECT * FROM Bid b WHERE request_id = :requestId order by price asc LIMIT 3")
    ArrayList<Bid> findByRequestId(@Param("requestId") Integer requestId);
}
