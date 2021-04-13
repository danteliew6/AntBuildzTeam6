package com.antbuildz.team6.repositories;

import com.antbuildz.team6.models.LorryCrane;
import com.antbuildz.team6.models.Transport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface LorryCraneRepository extends CrudRepository<LorryCrane,String> {
}
