package com.eParking.Egesha.api.dto;

    
public class AdminLoginResponse {

    private String token;
    private boolean success;
    private String message;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAdmin(String username, Integer id) {
    }
}
