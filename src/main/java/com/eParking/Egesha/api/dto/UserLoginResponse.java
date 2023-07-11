package com.eParking.Egesha.api.dto;

class LocalUserDetails{
    private String firstName;
    private String lastName;
    private Long phoneNumber;
    private String email;
    private Integer userId;

    public LocalUserDetails(String firstName, String lastName, Long phoneNumber, String email, Integer userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

public class UserLoginResponse {
    private boolean success;
    private String token;
    private String message;
    private LocalUserDetails user;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalUserDetails getUser() {
        return user;
    }

    public void setUser(Integer userId, String firstName, String lastName, Long phoneNumber, String email, String password) {
        this.user = new LocalUserDetails(firstName, lastName, phoneNumber, email, userId);
    }

    public void setUser(String firstName, String email, Integer userId, Long phoneNumber, String lastName) {
        this.user = new LocalUserDetails(firstName, lastName, phoneNumber, email, userId);
    }
}