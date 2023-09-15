package com.eParking.Egesha.model.dao;

import com.eParking.Egesha.model.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Rents extends JpaRepository <Rent, String> {

    Optional<Rents> findByAgentId (Integer  agentId);

}
