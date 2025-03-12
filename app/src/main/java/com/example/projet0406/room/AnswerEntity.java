package com.example.projet0406.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "answer_cache")
public class AnswerEntity {
    @PrimaryKey(autoGenerate = true)
    public int localId;
    public int userId;
    public int sondageId;
    public String reponse;
    public boolean isSynced;

    public AnswerEntity(int userId, int sondageId, String reponse, boolean isSynced) {
        this.userId = userId;
        this.sondageId = sondageId;
        this.reponse = reponse;
        this.isSynced = isSynced;
    }
}