package com.eParking.Egesha.api.dto;

import com.eParking.Egesha.model.ParkingLots;

public class AvailableSpotsBody {

    private String spotName;
    private boolean isAvailable;
    private ParkingLots parkingLotId;

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

    public ParkingLots getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(ParkingLots parkingLotId) {
        this.parkingLotId = parkingLotId;
    }
}
