package com.antbuildz.antbuildz.repositories;


import com.antbuildz.antbuildz.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
}
