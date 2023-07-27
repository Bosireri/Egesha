package com.eParking.Egesha.api.controller.auth;

import com.eParking.Egesha.model.AvailableSpots;
import com.eParking.Egesha.model.Booking;
import com.eParking.Egesha.model.ParkingLots;
import com.eParking.Egesha.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/admin/auth")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public ResponseEntity<Booking> bookParkingSlot(
            @RequestParam Integer parkingLotId,
            @RequestParam Integer availableSpotId,
            @RequestParam String date,
            @RequestParam String from,
            @RequestParam String to) {

        try {
            LocalDate bookingDate = LocalDate.parse(date);
            LocalTime bookingFrom = LocalTime.parse(from);
            LocalTime bookingTo = LocalTime.parse(to);

            Booking booking = bookingService.bookParkingSlot(parkingLotId, availableSpotId, bookingDate, bookingFrom, bookingTo);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{bookingId}/check-availability/{spotName}")
    public ResponseEntity<Boolean> isParkingSpotAvailable(@PathVariable Integer bookingId,
                                                          @PathVariable String spotName) {
        boolean isAvailable = bookingService.isParkingSpotAvailable(bookingId, spotName);
        return ResponseEntity.ok(isAvailable);
    }

    @GetMapping("getBookingById/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Integer bookingId) {
        try {
            Booking booking = bookingService.getBookingById(bookingId);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/getAllBookings")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    /*
    DIFFERENTIALS

     */
    @DeleteMapping("/cancelBooking/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable Integer bookingId) {
        try {
            bookingService.cancelBooking(bookingId);
            return ResponseEntity.ok("Booking cancelled");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}

