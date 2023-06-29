package com.eParking.Egesha.api.model;

import jakarta.validation.constraints.*;

public class RegistrationBody {

    @NotNull
    @NotBlank
    private String fullName;

    @NotNull
    @NotBlank
    @Size(min=4, max=20)
    private String username;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
    @Size(min=8, max=20)
    private String password;

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
