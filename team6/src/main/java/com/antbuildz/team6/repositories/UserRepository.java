package com.antbuildz.team6.repositories;

import com.antbuildz.team6.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
}
