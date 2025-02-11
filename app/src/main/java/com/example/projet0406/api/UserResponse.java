package com.example.projet0406.api;

public class UserResponse {
    private boolean success;
    private String message;
    private String token;
    private int sondeurId;

    public boolean isSuccess() {
        return success;
    }
    public String getMessage() {
        return message;
    }
    public String getToken() {
        return token;
    }
    public int getSondeurId() {
        return sondeurId;
    }
}