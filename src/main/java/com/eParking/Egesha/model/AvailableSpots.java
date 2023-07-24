package com.eParking.Egesha.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "available_spots")
public class AvailableSpots {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "spotName", nullable = false)
    private String spotName;

    @Column(name = "isAvailable", nullable = false)
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "parkingLotId")
    @JsonIgnore
    private ParkingLots parkingLot;

    public AvailableSpots() {
    }


    public AvailableSpots(Integer id, String spotName, boolean isAvailable, ParkingLots parkingLot) {
        this.id = id;
        this.spotName = spotName;
        this.isAvailable = isAvailable;
        this.parkingLot = parkingLot;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public ParkingLots getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLots parkingLot) {
        this.parkingLot = parkingLot;
    }
}
