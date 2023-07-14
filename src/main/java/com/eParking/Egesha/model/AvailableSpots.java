package com.eParking.Egesha.model;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "lot_id", nullable = false)
    private ParkingLots lots;

    public AvailableSpots() {
    }


    public AvailableSpots(Integer id, String spotName, boolean isAvailable, ParkingLots lots) {
        this.id = id;
        this.spotName = spotName;
        this.isAvailable = isAvailable;
        this.lots = lots;
    }

    public boolean isAvailable() {
        return isAvailable;
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
}
