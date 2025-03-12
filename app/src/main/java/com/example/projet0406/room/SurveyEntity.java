package com.example.projet0406.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "survey_cache")
public class SurveyEntity {

    @PrimaryKey
    public int idSurvey;
    public String title;
    public String description;

    public SurveyEntity(int idSurvey, String title, String description) {
        this.idSurvey = idSurvey;
        this.title = title;
        this.description = description;
    }
}