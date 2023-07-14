package com.eParking.Egesha.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "SpaceImage", nullable = false)
    @Transient
    private String spaceImage;

    @Column(name = "SpaceName", nullable = false)
    @Transient
    private String spaceName;

    @Column(name = "Location", nullable = false)
    @Transient
    private String location;

    @Column(name = "AmountPerHour")
    @Transient
    private String amount;

    @Column(name = "ParkingSpot", nullable = false)
    @Transient
    private String parkingSpot;

    @Column(name = "Date", nullable = false)
    private LocalDate date;

    @Column(name = "From(Time)", nullable = false)
    private LocalTime from;

    @Column(name = "To(Time)", nullable = false)
    private LocalTime to;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id")
    private ParkingLots parkingLot;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpaceImage() {
        if (spaceImage == null && parkingLot != null) {
            spaceImage = parkingLot.getSpaceImage();
        }
        return spaceImage;
    }

    public void setSpaceImage(String spaceImage) {
        this.spaceImage = spaceImage;
    }

    public String getSpaceName() {
        if (spaceName == null && parkingLot != null) {
            spaceName = parkingLot.getSpaceName();
        }
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public String getLocation() {
        if (location == null && parkingLot != null) {
            location = parkingLot.getLocation();
        }
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /*
    1. `if (parkingSpot == null && parkingLot != null && !parkingLot.getAvailableSpots().isEmpty())`:
   - This condition checks if the `parkingSpot` field is currently null and if the associated `ParkingLots` object (`parkingLot`) is not null.
   - It also checks if the `availableSpots` list in the `ParkingLots` object is not empty.
   - This condition ensures that the `parkingSpot` field is only populated if it is currently null and if there are available spots in the associated `ParkingLots` object.

2. `parkingSpot = parkingLot.getAvailableSpots().get(0).getSpotName();`:
   - If the above condition is true, this line retrieves the first available spot from the `availableSpots` list in the associated `ParkingLots` object.
   - It assumes that the `getAvailableSpots()` method returns a list of `AvailableSpots` objects, and `getSpotName()` retrieves the name of the spot for the first element (`get(0)`) in the list.

3. `return parkingSpot;`:
   - Finally, the method returns the value of the `parkingSpot` field, whether it has been populated with the first available spot or remains null if the conditions in the if statement were not satisfied.
     */
    public String getParkingSpot() {
        if (parkingSpot == null && parkingLot != null && !parkingLot.getAvailableSpots().isEmpty()) {
            // Assuming you want to get the first available spot from the list
            parkingSpot = parkingLot.getAvailableSpots().get(0).getSpotName();
        }
        return parkingSpot;
    }

    public void setParkingSpot(String parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getFrom() {
        return from;
    }

    public void setFrom(LocalTime from) {
        this.from = from;
    }

    public LocalTime getTo() {
        return to;
    }

    public void setTo(LocalTime to) {
        this.to = to;
    }

    public String getAmount() {
        if (amount == null && parkingLot != null) {
            amount = parkingLot.getAmount();
        }
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public ParkingLots getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLots parkingLot) {
        this.parkingLot = parkingLot;
    }
}