package com.eParking.Egesha.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticketId", nullable = false)
    private Integer ticketId;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "plateNumber", nullable = false, unique = true)
    private CarDetails carDetails;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "spaceNumber", nullable = false, unique = true)
    private ParkingSpace parkingSpace;

    @Column(name = "entryTime", nullable = false)
    private Double entryTime;

    @Column(name = "exitTime", nullable = false)
    private Double exitTime;

    @Column(name = "paymentStatus", nullable = false)
    private String paymentStatus;

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public CarDetails getCarDetails() {
        return carDetails;
    }

    public void setCarDetails(CarDetails carDetails) {
        this.carDetails = carDetails;
    }

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public Double getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Double entryTime) {
        this.entryTime = entryTime;
    }

    public Double getExitTime() {
        return exitTime;
    }

    public void setExitTime(Double exitTime) {
        this.exitTime = exitTime;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
