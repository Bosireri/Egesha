package com.eParking.Egesha.api.controller.auth;

import com.eParking.Egesha.model.Booking;
import com.eParking.Egesha.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/auth")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{bookingId}/check-availability/{spotName}")
    public ResponseEntity<Boolean> isParkingSpotAvailable(@PathVariable Integer bookingId,
                                                          @PathVariable String spotName) {
        boolean isAvailable = bookingService.isParkingSpotAvailable(bookingId, spotName);
        return ResponseEntity.ok(isAvailable);
    }

    @PostMapping("/bookParkingSpot")
    public ResponseEntity<String> bookParkingSpot(@RequestBody Booking booking) {
        try {
            bookingService.bookParkingSpot(booking);
            return ResponseEntity.ok("Booking successful");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/findBooking/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Integer bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(booking);
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

