package com.eParking.Egesha.model.dao;

import com.eParking.Egesha.model.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentsRepository extends JpaRepository <Rent, String> {

    Optional<RentsRepository> findByAgentId (Integer  agentId);

}
