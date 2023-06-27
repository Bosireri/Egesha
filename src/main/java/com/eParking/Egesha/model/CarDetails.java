package com.eParking.Egesha.model;

import jakarta.persistence.*;

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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contact", nullable = false, unique = true, length = 10)
    private String contact;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
