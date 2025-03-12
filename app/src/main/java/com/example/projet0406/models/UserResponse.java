package com.example.projet0406.models;

public class UserResponse {
    private boolean success;
    private String message;
    private String token;

    private int id_user;
    private String nom;
    private String prenom;
    private String email;
    private int id_role;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public int getIdUser() {
        return id_user;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public int getIdRole() {
        return id_role;
    }
}