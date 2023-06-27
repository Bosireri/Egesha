package com.eParking.Egesha.model;

import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name = "CarDetails")
public class CarDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carId", nullable = false)
    private Integer carId;

    @Column(name = "plateNumber", nullable = false, unique = true, length = 8)
    private String plateNumber;

    @Column(name = "carModel", nullable = false)
    private String carModel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ownerName", nullable = false)
    private LocalUser user;

    @Column(name = "contact", nullable = false, unique = true, length = 10)
    private String contact;

    @OneToOne(mappedBy = "carDetails", cascade = CascadeType.REMOVE, optional = false, orphanRemoval = true)
    private Ticket ticket;

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public LocalUser getUser() {
        return user;
    }

    public void setUser(LocalUser user) {
        this.user = user;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
