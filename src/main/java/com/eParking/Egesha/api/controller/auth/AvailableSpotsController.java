package com.eParking.Egesha.api.controller.auth;

import com.eParking.Egesha.api.dto.AvailableSpotsBody;
import com.eParking.Egesha.api.dto.SuccessAndMessage;
import com.eParking.Egesha.api.dto.UniversalResponse;
import com.eParking.Egesha.model.AvailableSpots;
import com.eParking.Egesha.model.ParkingLots;
import com.eParking.Egesha.model.dao.AvailableSpotsRepository;
import com.eParking.Egesha.model.dao.ParkingLotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/auth")
public class AvailableSpotsController {

    @Autowired
    private AvailableSpotsRepository availableSpotsRepository;
    private SuccessAndMessage successAndMessage;
    @Autowired
    ParkingLotsRepository parkingLotsRepository;


    @PostMapping("/createSpot/{id}")
    public ResponseEntity<SuccessAndMessage> createAvailableSpot(@RequestBody AvailableSpotsBody spotBody, @PathVariable Integer id) {
        try {
            // Fetch the corresponding ParkingLots object using the provided parkingLotId
            Optional<ParkingLots> optionalParkingLot = parkingLotsRepository.findById(id);
            if (!optionalParkingLot.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            ParkingLots parkingLot = optionalParkingLot.get();

            AvailableSpots spot = new AvailableSpots();
            spot.setSpotName(spotBody.getSpotName());
            spot.setAvailable(spotBody.isAvailable());
            spot.setParkingLot(parkingLot);

            AvailableSpots createdSpot = availableSpotsRepository.save(spot);
            if (createdSpot != null) {
                SuccessAndMessage response = new SuccessAndMessage();
                response.setSuccess(true);
                response.setMessage("Successfully created a new available spot.");
                return ResponseEntity.ok(response);
            } else {
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

//    @GetMapping("/getSpot/{id}")
//    public ResponseEntity<SuccessAndMessage> getAvailableSpotById(@PathVariable Integer id) {
//        Optional<AvailableSpots> optionalSpot = availableSpotsRepository.findById(id);
//        SuccessAndMessage response = new SuccessAndMessage();
//        if (optionalSpot.isPresent()) {
//            response.setSuccess(true);
//            response.setMessage("Successfully retrieved available spot with ID: " + id);
//            return ResponseEntity.ok(response);
//        } else {
//            response.setSuccess(false);
//            response.setMessage("Available spot with ID: " + id + " not found.");
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/getSpot/{id}")
    public UniversalResponse getAvailableSpotById(@PathVariable("id") Integer id) {
        AvailableSpots availableSpots =availableSpotsRepository.findById((id)).orElse(null);
        if (availableSpots==null)
            return UniversalResponse.builder()
                    .message("Available Spot not found")
                    .status(404)
                    .data(null)
                    .errors(null)
                    .build();
        return UniversalResponse.builder()
                .message("Available Spot retrieved")
                .status(200)
                .data(availableSpots)
                .build();
    }

    @PutMapping("/updateSpot/{id}")
    public ResponseEntity<SuccessAndMessage> updateAvailableSpot(@PathVariable Integer id, @RequestBody AvailableSpots updatedSpot) {
        Optional<AvailableSpots> optionalSpot = availableSpotsRepository.findById(id);
        SuccessAndMessage response = new SuccessAndMessage();
        if (optionalSpot.isPresent()) {
            AvailableSpots spot = optionalSpot.get();

            spot.setSpotName(updatedSpot.getSpotName());
            spot.setAvailable(updatedSpot.isAvailable());

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
    public List<AvailableSpots> getAllAvailableSpots() {
        return availableSpotsRepository.findAll();
    }

    @DeleteMapping("/deleteSpot/{id}")
    public ResponseEntity<SuccessAndMessage> deleteAvailableSpot(@PathVariable Integer id) {
        Optional<AvailableSpots> optionalSpot = availableSpotsRepository.findById(id);
        SuccessAndMessage response = new SuccessAndMessage();
        if (optionalSpot.isPresent()) {
            availableSpotsRepository.deleteById(id);
            response.setSuccess(true);
            response.setMessage("Successfully deleted available spot of ID: " + id);
            return ResponseEntity.ok(response);
        } else {
            response.setSuccess(false);
            response.setMessage("Available spot with ID: " + id + " not found.");
            return ResponseEntity.notFound().build();
        }
    }
}
