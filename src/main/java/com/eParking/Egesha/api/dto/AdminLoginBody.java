package com.eParking.Egesha.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AdminLoginBody {

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String password;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}