package com.example.projet0406.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AnswerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAnswer(AnswerEntity answer);

    @Query("SELECT * FROM answer_cache WHERE isSynced = 0")
    List<AnswerEntity> getUnsyncedAnswers();

    @Update
    void updateAnswer(AnswerEntity answer);
}