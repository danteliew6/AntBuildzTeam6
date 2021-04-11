package com.antbuildz.team6.repositories;

import com.antbuildz.team6.models.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface RequestRepository extends CrudRepository<Request,Integer> {

    @Query("SELECT r FROM Request r WHERE user_email = LOWER(:email)")
    ArrayList<Request> findByEmail(@Param("email") String email);

}
