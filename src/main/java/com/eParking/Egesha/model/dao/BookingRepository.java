package com.eParking.Egesha.model.dao;

import com.eParking.Egesha.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository {
    Optional<Booking> findById(Integer id);
    boolean existsById(Integer id);
}
