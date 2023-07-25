package com.eParking.Egesha.api.controller.auth;

import com.eParking.Egesha.api.dto.SuccessAndMessage;
import com.eParking.Egesha.api.dto.UniversalResponse;
import com.eParking.Egesha.model.ParkingLots;
import com.eParking.Egesha.model.dao.ParkingLotsRepository;
import com.eParking.Egesha.service.FormService;
import com.eParking.Egesha.service.ImageUploadService;
import io.jsonwebtoken.io.IOException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin/auth")
@Slf4j
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
//    @PutMapping("/updateParkingLot/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PutMapping(value = "/updateParkingLot/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SuccessAndMessage> updateParkingLot(@PathVariable Integer id, @ModelAttribute ParkingLots updatedParkingLot) {
        SuccessAndMessage response = new SuccessAndMessage();
        ParkingLots existingParkingLot = parkingLotsRepository.findById(id).orElse(null);
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
        }
        response.setMessage( "Parking lot not found");
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/displayLot/{id}")
    public UniversalResponse getParkingLot(@PathVariable("id") Integer id) {
        ParkingLots parkingLots =parkingLotsRepository.findById((id)).orElse(null);
        if (parkingLots==null)
            return UniversalResponse.builder()
                    .message("parking not found")
                    .status(404)
                    .data(null)
                    .errors(null)
                    .build();
        return UniversalResponse.builder()
                .message("parking lot retrieved")
                .status(200)
                .data(parkingLots)
                .build();
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
