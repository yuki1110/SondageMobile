package com.example.projet0406.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {SurveyEntity.class, AnswerEntity.class}, version = 2)
public abstract class AsklyDatabase extends RoomDatabase {
    public abstract SurveyDao surveyDao();
    public abstract AnswerDao answerDao();
}