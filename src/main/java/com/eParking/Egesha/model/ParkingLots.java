package com.eParking.Egesha.model;

import jakarta.persistence.*;

@Entity
@Table(name = "parking_lots")
public class ParkingLots {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "spaceImage", nullable = false)
    private String spaceImage;

    @Column(name = "spaceName", nullable = false)
    private String spaceName;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "availableSlots", nullable = false)
    private Long availableSlots;

    @Column(name = "parkingFeatures", nullable = false)
    private Long parkingFeatures;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpaceImage() {
        return spaceImage;
    }

    public void setSpaceImage(String spaceImage) {
        this.spaceImage = spaceImage;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(Long availableSlots) {
        this.availableSlots = availableSlots;
    }

    public Long getParkingFeatures() {
        return parkingFeatures;
    }

    public void setParkingFeatures(Long parkingFeatures) {
        this.parkingFeatures = parkingFeatures;
    }
}