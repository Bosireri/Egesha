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

    @Column(name = "spot")
    private String spot;

    @ManyToOne(optional = false)
    @JoinColumn(name = "lot_id", nullable = false)
    private ParkingLots lots;

    public AvailableSpots() {
    }

    public AvailableSpots(Integer id, String spot) {
        this.id = id;
        this.spot = spot;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }
}
