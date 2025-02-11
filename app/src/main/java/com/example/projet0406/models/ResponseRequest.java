package com.example.projet0406.models;

public class ResponseRequest {
    private int sondeurId;
    private int sondageId;
    private String reponse;

    public ResponseRequest(int sondeurId, int sondageId, String reponse) {
        this.sondeurId = sondeurId;
        this.sondageId = sondageId;
        this.reponse = reponse;
    }

    public int getSondeurId() {
        return sondeurId;
    }
    public int getSondageId() {
        return sondageId;
    }
    public String getReponse() {
        return reponse;
    }
}