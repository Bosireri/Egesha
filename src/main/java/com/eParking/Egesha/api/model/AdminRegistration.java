package com.eParking.Egesha.api.model;

import jakarta.validation.constraints.*;

public class AdminRegistration {

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
    @Size(min=8, max=20)
    private String password;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
