package com.eParking.Egesha.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ParkingSpace")
public class ParkingSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spaceId", nullable = false)
    private Integer spaceId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "parkingLotId", nullable = false)
    private ParkingLots Lots;

    @Column(name = "spaceNumber", nullable = false, unique = true)
    private String spaceNumber;

    @Column(name = "occupied", nullable = false)
    private Boolean occupied;

    @OneToOne(mappedBy = "parkingSpace", cascade = CascadeType.REMOVE, optional = false, orphanRemoval = true)
    private Ticket ticket;

    public Integer getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Integer spaceId) {
        this.spaceId = spaceId;
    }

    public ParkingLots getLots() {
        return Lots;
    }

    public void setLots(ParkingLots lots) {
        Lots = lots;
    }

    public String getSpaceNumber() {
        return spaceNumber;
    }

    public void setSpaceNumber(String spaceNumber) {
        this.spaceNumber = spaceNumber;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
