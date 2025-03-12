package com.example.projet0406.models;

public class Survey {
    private int idSurvey;
    private String title;
    private String description;

    public Survey() {}

    public Survey(int idSurvey, String title, String description) {
        this.idSurvey = idSurvey;
        this.title = title;
        this.description = description;
    }

    // GETTERS
    public int getIdSurvey() {
        return idSurvey;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }

    // SETTERS
    public void setIdSurvey(int idSurvey) {
        this.idSurvey = idSurvey;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}