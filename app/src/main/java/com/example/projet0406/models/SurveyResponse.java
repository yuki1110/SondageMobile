package com.example.projet0406.models;

import java.util.List;

public class SurveyResponse {
    private String status;
    private String message;
    private List<Survey> sondages;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Survey> getSondages() {
        return sondages;
    }
}
