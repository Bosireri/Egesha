package com.eParking.Egesha.api.controller.auth;

import com.eParking.Egesha.api.dto.SuccessAndMessage;
import com.eParking.Egesha.model.AvailableSpots;
import com.eParking.Egesha.model.dao.AvailableSpotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/auth")
public class AvailableSpotsController {

    @Autowired
    private AvailableSpotsRepository availableSpotsRepository;
    private SuccessAndMessage successAndMessage;


    @PostMapping("/createSpot")
    public ResponseEntity<SuccessAndMessage> createAvailableSpot(@RequestBody AvailableSpots spot) {
        try {
            AvailableSpots createdSpot = availableSpotsRepository.save(spot);
            if (createdSpot != null) {
                SuccessAndMessage response = new SuccessAndMessage();
                response.setSuccess(true);
                response.setMessage("Successfully created a new available spot.");
                return ResponseEntity.ok(response);
            } else {
                // Some issue with database, record not saved
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } catch (Exception e) {
            // Handle database or other internal errors
            SuccessAndMessage response = new SuccessAndMessage();
            response.setSuccess(false);
            response.setMessage("Failed to create an available spot. Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/getSpot/{id}")
    public ResponseEntity<SuccessAndMessage> getAvailableSpotById(@PathVariable Integer id) {
        Optional<AvailableSpots> optionalSpot = availableSpotsRepository.findById(id);
        SuccessAndMessage response = new SuccessAndMessage();
        if (optionalSpot.isPresent()) {
            response.setSuccess(true);
            response.setMessage("Successfully retrieved available spot with ID: " + id);
            return ResponseEntity.ok(response);
        } else {
            response.setSuccess(false);
            response.setMessage("Available spot with ID: " + id + " not found.");
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateSpot/{id}")
    public ResponseEntity<SuccessAndMessage> updateAvailableSpot(@PathVariable Integer id, @RequestBody AvailableSpots updatedSpot) {
        Optional<AvailableSpots> optionalSpot = availableSpotsRepository.findById(id);
        SuccessAndMessage response = new SuccessAndMessage();
        if (optionalSpot.isPresent()) {
            AvailableSpots spot = optionalSpot.get();
            // Update properties of the existing spot
            spot.setSpotName(updatedSpot.getSpotName());
            spot.setAvailable(updatedSpot.isAvailable());
            // Update other properties as needed

            availableSpotsRepository.save(spot);
            response.setSuccess(true);
            response.setMessage("Successfully updated available spot with ID: " + id);
            return ResponseEntity.ok(response);
        } else {
            response.setSuccess(false);
            response.setMessage("Available spot with ID: " + id + " not found.");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllSpots")
    public ResponseEntity<SuccessAndMessage> getAllAvailableSpots() {
        List<AvailableSpots> spots = availableSpotsRepository.findAll();
        SuccessAndMessage response = new SuccessAndMessage();
        response.setSuccess(true);
        response.setMessage("Successfully retrieved all available spots.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteSpot/{id}")
    public ResponseEntity<SuccessAndMessage> deleteAvailableSpot(@PathVariable Integer id) {
        Optional<AvailableSpots> optionalSpot = availableSpotsRepository.findById(id);
        SuccessAndMessage response = new SuccessAndMessage();
        if (optionalSpot.isPresent()) {
            availableSpotsRepository.deleteById(id);
            response.setSuccess(true);
            response.setMessage("Successfully deleted available spot with ID: " + id);
            return ResponseEntity.ok(response);
        } else {
            response.setSuccess(false);
            response.setMessage("Available spot with ID: " + id + " not found.");
            return ResponseEntity.notFound().build();
        }
    }
}
