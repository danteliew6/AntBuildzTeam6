package com.antbuildz.team6.repositories;

import com.antbuildz.team6.models.Request;
import com.antbuildz.team6.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.ArrayList;

public interface UserRepository extends CrudRepository<User,String> {

    @Query("SELECT u FROM User u WHERE is_verified = 0")
    ArrayList<User> findAllUnverified();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User SET is_verified = 1 WHERE email = :email")
    int verifyUser(@Param("email") String email);

}
