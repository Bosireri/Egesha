package com.eParking.Egesha.model.dao;

import com.eParking.Egesha.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository <UserProfile, Long>{
    Optional<UserProfile> findByEmailIgnoreCase(String email);
    Boolean existsByEmail(String email);

}
