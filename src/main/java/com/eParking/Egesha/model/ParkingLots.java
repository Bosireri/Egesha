package com.eParking.Egesha.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "AmountPerHour", nullable = false)
    private String amount;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "SecurityFeatures", nullable = false)
    private String securityFeatures;

    @Column(name = "AboutFeatures", nullable = false)
    private String aboutFeatures;

    @OneToMany(mappedBy = "parkingLot", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvailableSpots> availableSpots;

    public List<AvailableSpots> getAvailableSpots() {
        return availableSpots;
    }

    public void setAvailableSpots(List<AvailableSpots> availableSpots) {
        this.availableSpots = availableSpots;
    }

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

    public String getSecurityFeatures() {
        return securityFeatures;
    }

    public void setSecurityFeatures(String securityFeatures) {
        this.securityFeatures = securityFeatures;
    }

    public String getAboutFeatures() {
        return aboutFeatures;
    }

    public void setAboutFeatures(String aboutFeatures) {
        this.aboutFeatures = aboutFeatures;
    }
}