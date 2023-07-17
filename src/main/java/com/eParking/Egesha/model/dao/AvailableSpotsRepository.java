package com.eParking.Egesha.model.dao;

import com.eParking.Egesha.model.AvailableSpots;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface AvailableSpotsRepository extends JpaRepository<AvailableSpots, Integer> {

    Optional<AvailableSpots> findAvailableSpotsById(Integer id);

}
