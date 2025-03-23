package com.example.projet0406.models;

import com.google.gson.annotations.SerializedName;

public class Survey {
    @SerializedName("id")
    private int idSurvey;

    @SerializedName("titre")
    private String title;

    @SerializedName("description")
    private String description;

    public Survey() {}

    public Survey(int idSurvey, String title, String description) {
        this.idSurvey = idSurvey;
        this.title = title;
        this.description = description;
    }

    public int getIdSurvey() {
        return idSurvey;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

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
