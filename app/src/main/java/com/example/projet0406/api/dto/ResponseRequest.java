package com.example.projet0406.api.dto;

import java.util.List;

public class ResponseRequest {
    private int userId;
    private int sondageId;
    private List<String> reponses;

    public ResponseRequest(int userId, int sondageId, List<String> reponses) {
        this.userId = userId;
        this.sondageId = sondageId;
        this.reponses = reponses;
    }

    public int getUserId() {
        return userId;
    }

    public int getSondageId() {
        return sondageId;
    }

    public List<String> getReponses() {
        return reponses;
    }
}