package com.eParking.Egesha.service;

import com.eParking.Egesha.api.dto.SuccessAndMessage;
import com.eParking.Egesha.exception.ParkingLotExistsException;
import com.eParking.Egesha.model.ParkingLots;
import com.eParking.Egesha.model.dao.ParkingLotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FormService {

    @Autowired
    private ParkingLotsRepository parkingLotsRepository;

    public ParkingLots mapData(String data, Class<ParkingLots> parkingLotsClass) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().readValue(data, parkingLotsClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<SuccessAndMessage> registerParkingLots(ParkingLots parkingLots) {
        try {
            if (parkingLotsRepository.existsBySpaceName(parkingLots.getSpaceName())) {
                throw new ParkingLotExistsException("Parking Lot already exists");
            }

            ParkingLots savedParkingLot = parkingLotsRepository.save(parkingLots);
            SuccessAndMessage response = new SuccessAndMessage();
            response.setSuccess(true);
            response.setMessage("Parking Lot Registered Successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ParkingLotExistsException e) {
            SuccessAndMessage response = new SuccessAndMessage();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            SuccessAndMessage response = new SuccessAndMessage();
            response.setSuccess(false);
            response.setMessage("Failed to register Parking Lot");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
