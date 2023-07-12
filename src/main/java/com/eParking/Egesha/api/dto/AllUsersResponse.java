package com.eParking.Egesha.api.dto;

import java.util.ArrayList;

public class AllUsersResponse {

    private boolean success;
    private String message;
    private ArrayList<LocalUserDetails> users;

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

    public ArrayList<LocalUserDetails> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<LocalUserDetails> users) {
        this.users = users;
    }
}
