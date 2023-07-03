package com.eParking.Egesha.model.dao;


import com.eParking.Egesha.model.LocalUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LocalUserRepository extends CrudRepository<LocalUser, Long> {

    Optional<LocalUser> findByPhoneNumber(Integer phoneNumber);
    Optional<LocalUser> findByEmailIgnoreCase(String email);

}
