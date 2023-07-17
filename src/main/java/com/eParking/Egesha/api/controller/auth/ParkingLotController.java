package com.eParking.Egesha.api.controller.auth;

import com.eParking.Egesha.api.dto.SuccessAndMessage;
import com.eParking.Egesha.model.ParkingLots;
import com.eParking.Egesha.model.dao.ParkingLotsRepository;
import com.eParking.Egesha.service.FormService;
import com.eParking.Egesha.service.ImageUploadService;
import io.jsonwebtoken.io.IOException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/auth")
public class ParkingLotController {

    @Autowired
    private ParkingLotsRepository parkingLotsRepository;
    @Autowired
    private ImageUploadService imageUploadService;
    @Autowired
    private FormService formService;

    @PostMapping("/createParkingLot")
    public ResponseEntity<SuccessAndMessage> registerParkingLots(@Valid @RequestPart("data") String data,
                                                                                @RequestPart("file") MultipartFile file,
                                                                                @RequestHeader(name = "Authorization") String token) {
        try {
            ParkingLots parkingLots = formService.mapData(data, ParkingLots.class);
            String filePath = imageUploadService.uploadImage(file);
            parkingLots.setSpaceImage(filePath);
            return formService.registerParkingLots(parkingLots);
        } catch (IOException e) {
            SuccessAndMessage response = new SuccessAndMessage();
            response.setSuccess(false);
            response.setMessage("Failed to upload image");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to update a registered parking lot
    @PutMapping("/updateParkingLot/{id}")
    public ResponseEntity<SuccessAndMessage> updateParkingLot(Integer lotId, ParkingLots updatedParkingLot) {
        SuccessAndMessage response = new SuccessAndMessage();
        ParkingLots existingParkingLot = parkingLotsRepository.findById(lotId).orElse(null);
        if (existingParkingLot != null) {

            existingParkingLot.setSpaceName(updatedParkingLot.getSpaceName());
            existingParkingLot.setLocation(updatedParkingLot.getLocation());
            existingParkingLot.setAmount(updatedParkingLot.getAmount());
            existingParkingLot.setDescription(updatedParkingLot.getDescription());
            existingParkingLot.setSecurityFeatures(updatedParkingLot.getSecurityFeatures());
            existingParkingLot.setAboutFeatures(updatedParkingLot.getAboutFeatures());
            existingParkingLot.setAvailableSpots(updatedParkingLot.getAvailableSpots());

            ParkingLots updatedLot = parkingLotsRepository.save(existingParkingLot);
            response.setMessage("Parking lot updated Successfully");
            response.setSuccess(true);
            return new ResponseEntity<SuccessAndMessage>(response, HttpStatus.OK);
//            return new SuccessAndMessage(true, "Parking lot updated successfully", updatedLot);
        }
        response.setMessage( "Parking lot not found");
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
//        return new SuccessAndMessage(false,"Parking lot not found", null);
    }

    @GetMapping("/displayLot/{id}")
    public ResponseEntity<ParkingLots> getParkingLot(@PathVariable("id") Integer id) {
        Optional<ParkingLots> optionalParkingLot = parkingLotsRepository.findById(id);
        // Check if the parking lot exists
        if (optionalParkingLot.isPresent()) {
            // Return the parking lot as the response
            return ResponseEntity.ok(optionalParkingLot.get());
        } else {
            // Return an error response with a message
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteParkingLot/{id}")
    public ResponseEntity<SuccessAndMessage> deleteParkingLot(@PathVariable("id") Integer id) {
        SuccessAndMessage response = new SuccessAndMessage();
        // Check if the parking lot exists in the repository
        if (parkingLotsRepository.existsById(id)) {
            parkingLotsRepository.deleteById(id);
            response.setMessage("Parking lot deleted successfully");
            response.setSuccess(true);
            return new ResponseEntity<SuccessAndMessage>(response, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/displayParkingLots")
    public List<ParkingLots> findAllParkingLots() {
        return parkingLotsRepository.findAll();
    }
}
