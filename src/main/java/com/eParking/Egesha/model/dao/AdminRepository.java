package com.eParking.Egesha.model.dao;

import com.eParking.Egesha.model.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin, Long> {

    Optional<Admin> findByUsername(String username);
    Boolean existsByUsername(String username);
}
