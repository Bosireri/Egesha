package com.eParking.Egesha.model.dao;


import com.eParking.Egesha.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface LocalUserRepository extends JpaRepository<LocalUser, Integer> {

    Optional<LocalUser> findByPhoneNumber(Long phoneNumber);
    boolean existsByPhoneNumber(Long phoneNumber);
    Optional<LocalUser> findByEmailIgnoreCase(String email);
    boolean existsByEmail(String email);
}
