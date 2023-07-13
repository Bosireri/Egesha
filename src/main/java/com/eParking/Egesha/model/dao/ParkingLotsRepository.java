package com.eParking.Egesha.model.dao;

import com.eParking.Egesha.model.ParkingLots;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotsRepository extends JpaRepository<ParkingLots,Integer> {
    boolean findBySpaceNameIgnoreCase(String spaceName);
    boolean existsBySpaceName(String spaceName);
}
