package com.example.projet0406.api;

public class SondeurRequest {
    private int sondeurId;

    public SondeurRequest(int sondeurId) {
        this.sondeurId = sondeurId;
    }
    public int getSondeurId() {
        return sondeurId;
    }
    public void setSondeurId(int sondeurId) {
        this.sondeurId = sondeurId;
    }
}