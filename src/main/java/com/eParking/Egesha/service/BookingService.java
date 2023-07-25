package com.eParking.Egesha.service;

import com.eParking.Egesha.model.AvailableSpots;
import com.eParking.Egesha.model.Booking;
import com.eParking.Egesha.model.ParkingLots;
import com.eParking.Egesha.model.dao.AvailableSpotsRepository;
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
    private final AvailableSpotsRepository availableSpotsRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, ParkingLotsRepository parkingLotsRepository, AvailableSpotsRepository availableSpotsRepository) {
        this.bookingRepository = bookingRepository;
        this.parkingLotsRepository = parkingLotsRepository;
        this.availableSpotsRepository = availableSpotsRepository;
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

    public Booking bookParkingSlot(Integer parkingLotId, Integer availableSpotId, LocalDate date, LocalTime from, LocalTime to) {
        ParkingLots parkingLot = parkingLotsRepository.findById(parkingLotId)
                .orElseThrow(() -> new RuntimeException("Parking lot not found with ID: " + parkingLotId));

        AvailableSpots selectedSpot = availableSpotsRepository.findById(availableSpotId)
                .orElseThrow(() -> new RuntimeException("Available spot not found with ID: " + availableSpotId));

        Booking booking = new Booking();
        booking.setParkingSpot(selectedSpot.getSpotName());
        booking.setSpaceImage(parkingLot.getSpaceImage());
        booking.setSpaceName(parkingLot.getSpaceName());
        booking.setLocation(parkingLot.getLocation());
        booking.setAmount(parkingLot.getAmount());
        booking.setDate(date);
        booking.setFrom(from);
        booking.setTo(to);

        // Save the booking in the database
        Booking savedBooking = bookingRepository.save(booking);

        // Mark the selected spot as booked
        selectedSpot.setAvailable(false);
        availableSpotsRepository.save(selectedSpot);

        return savedBooking;
    }

    public Booking getBookingById(Integer bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /*
    DIFFERENTIALS

     */
    @Transactional
    public void cancelBooking(Integer bookingId) {
        Booking booking = getBookingById(bookingId);

        // Perform additional checks or business logic before cancellation

        bookingRepository.delete(booking);
    }
}
