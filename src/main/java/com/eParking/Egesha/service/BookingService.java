package com.eParking.Egesha.service;

import com.eParking.Egesha.model.AvailableSpots;
import com.eParking.Egesha.model.Booking;
import com.eParking.Egesha.model.ParkingLots;
import com.eParking.Egesha.model.dao.BookingRepository;
import com.eParking.Egesha.model.dao.ParkingLotsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ParkingLotsRepository parkingLotsRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, ParkingLotsRepository parkingLotsRepository) {
        this.bookingRepository = bookingRepository;
        this.parkingLotsRepository = parkingLotsRepository;
    }

    //check the availability of a parking spot based on a given Booking object.
    public boolean isParkingSpotAvailable(Integer bookingId, String spotName) {
        Booking booking = getBookingById(bookingId);
        return isParkingSpotAvailable(bookingId, spotName);
    }

    public boolean isParkingSpotAvailable(Booking booking) {
        ParkingLots parkingLot = booking.getParkingLot();
        String selectedSpot = booking.getParkingSpot();

        List<AvailableSpots> availableSpots = parkingLot.getAvailableSpots();

        return availableSpots.stream()
                .anyMatch(spot -> spot.getSpotName().equals(selectedSpot) && spot.isAvailable());
    }

    public void bookParkingSpot(Booking booking) {
        if (isParkingSpotAvailable(booking)) {
            // Set the current timestamp as the booking time
            booking.setDate(LocalDate.now());
            booking.setFrom(LocalTime.now());
            booking.setTo(LocalTime.now());

            bookingRepository.save(booking);
        } else {
            throw new IllegalArgumentException("The selected parking spot is not available.");
        }
    }

    public Booking getBookingById(Integer bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /*
    DIFFERENTIALS

    @Transactional
    public void cancelBooking(Integer bookingId) {
        Booking booking = getBookingById(bookingId);

        // Perform additional checks or business logic before cancellation

        bookingRepository.delete(booking);
    }

     */
}
