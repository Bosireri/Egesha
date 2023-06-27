package com.eParking.Egesha.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ParkingLots")
public class ParkingLots {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lotId", nullable = false)
    private Integer lotId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "totalCapacity", nullable = false)
    private String totalCapacity;

    @Column(name = "availableCapacity", nullable = false)
    private String availableCapacity;

    @OneToMany(mappedBy = "Lots", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ParkingSpace> spaces = new ArrayList<>();

    public Integer getLotId() {
        return lotId;
    }

    public void setLotId(Integer lotId) {
        this.lotId = lotId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(String totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public String getAvailableCapacity() {
        return availableCapacity;
    }

    public void setAvailableCapacity(String availableCapacity) {
        this.availableCapacity = availableCapacity;
    }

    public List<ParkingSpace> getSpaces() {
        return spaces;
    }

    public void setSpaces(List<ParkingSpace> spaces) {
        this.spaces = spaces;
    }
}
