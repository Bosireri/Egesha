package com.eParking.Egesha.model;

import jakarta.persistence.*;

@Entity
@Table(name = "parking_lots")
public class ParkingLots {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "SpaceImage", nullable = false)
    private String spaceImage;

    @Column(name = "SpaceName", nullable = false)
    private String spaceName;

    @Column(name = "Location", nullable = false)
    private String location;

    @Column(name = "Amount", nullable = false)
    private String amount;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "AvailableSlots", nullable = false)
    private String availableSlots;

    @Column(name = "ParkingFeatures", nullable = false)
    private String parkingFeatures;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(String availableSlots) {
        this.availableSlots = availableSlots;
    }

    public String getParkingFeatures() {
        return parkingFeatures;
    }

    public void setParkingFeatures(String parkingFeatures) {
        this.parkingFeatures = parkingFeatures;
    }
}