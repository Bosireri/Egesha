package com.eParking.Egesha.api.dto;

import jakarta.validation.constraints.*;

public class LoginBody {

//    @NotNull(message = "Phone Number is required")
//    @Min(value = 1000000000L, message = "Phone Number must have at least 10 digits")
//    @Max(value = 9999999999999L, message = "Phone Number must have at most 13 digits")
//    private Long phoneNumber;
    @Email
    private String email;

    @NotNull
    @NotBlank
    private String password;

//    public Long getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(Long phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
