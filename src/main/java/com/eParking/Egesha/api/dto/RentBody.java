package com.eParking.Egesha.api.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class RentBody {

    @NotNull
    private String location;

    @NotNull
    private String dates;

    @NotNull
    private String operator;

    public String getLocation() {
        return location;
    }

    public String getDates() {
        return dates;
    }

    public String getOperator() {
        return operator;
    }
}
