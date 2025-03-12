package com.example.projet0406.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.projet0406.models.UserResponse;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "app_prefs";
    private static final String KEY_USER_ID = "id_user";
    private static final String KEY_ROLE_ID = "id_role";
    private static final String KEY_TOKEN = "token";

    private static SharedPrefManager instance;
    private SharedPreferences sharedPreferences;

    private SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public void saveUser(int idUser, String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, idUser);
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.contains(KEY_USER_ID);
    }

    public int getIdUser() {
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }

    public int getIdRole() {
        return sharedPreferences.getInt(KEY_ROLE_ID, -1);
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}