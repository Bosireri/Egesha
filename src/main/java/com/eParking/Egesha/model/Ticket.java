package com.eParking.Egesha.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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
    private LocalDateTime entryTime;

    @Column(name = "exitTime", nullable = false)
    private LocalDateTime exitTime;

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

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
