package com.example.projet0406.room;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private static AsklyDatabase instance;

    public static synchronized AsklyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AsklyDatabase.class,
                            "askly_db"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}