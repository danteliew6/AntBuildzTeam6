package com.antbuildz.team6.repositories;

import com.antbuildz.team6.models.Bid;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.ArrayList;

public interface BidRepository extends CrudRepository<Bid,Integer> {

    @Query("SELECT b FROM Bid b WHERE partner_email = LOWER(:email)")
    ArrayList<Bid> findByEmail(@Param("email") String email);

    @Query(nativeQuery = true, value = "SELECT * FROM Bid b WHERE request_id = :requestId order by price asc LIMIT 3")
    ArrayList<Bid> findByRequestId(@Param("requestId") Integer requestId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Bid SET is_selected = 2 WHERE request_id = :requestId AND is_selected = 0")
    void rejectRemainingBids(@Param("requestId") Integer requestId);

    @Query("SELECT b FROM Bid b WHERE partner_email = LOWER(:email) AND is_selected = 1")
    ArrayList<Bid> findMyAcceptedBids(@Param("email") String email);


}
