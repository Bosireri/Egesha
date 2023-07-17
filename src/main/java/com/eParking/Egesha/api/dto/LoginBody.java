package com.eParking.Egesha.api.dto;

import jakarta.validation.constraints.*;

public class LoginBody {

    @NotNull(message = "Phone Number is required")
    @Min(value = 1000000000L, message = "Phone Number must have at least 10 digits")
    @Max(value = 9999999999999L, message = "Phone Number must have at most 13 digits")
    private String phoneNumber;

    @NotNull
    @NotBlank
    private String password;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
