package com.eParking.Egesha.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoginBody {

    @NotNull(message = "Phone Number is required")
    @Min(value = 1000000000L, message = "Phone Number must have at least 10 digits")
    @Max(value = 9999999999999L, message = "Phone Number must have at most 13 digits")
    private Long phoneNumber;

    @NotNull
    @NotBlank
    private String password;

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}
