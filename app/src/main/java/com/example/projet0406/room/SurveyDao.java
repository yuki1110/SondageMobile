package com.example.projet0406.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SurveyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SurveyEntity> surveys);

    @Query("SELECT * FROM survey_cache")
    LiveData<List<SurveyEntity>> getAllSurveys();
}