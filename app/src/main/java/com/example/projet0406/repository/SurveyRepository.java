package com.example.projet0406.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.projet0406.api.ApiService;
import com.example.projet0406.api.RetrofitClient;
import com.example.projet0406.models.Survey;
import com.example.projet0406.room.DatabaseClient;
import com.example.projet0406.room.SurveyDao;
import com.example.projet0406.room.SurveyEntity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyRepository {

    private final SurveyDao surveyDao;
    private final ApiService apiService;

    public SurveyRepository(Context context) {
        // Récupération du DAO
        surveyDao = DatabaseClient.getInstance(context).surveyDao();

        // Récupération de l'API
        apiService = RetrofitClient.getInstance().getApi();
    }

    /**
     * Retourne la liste des sondages stockés localement (Room)
     * sous forme de LiveData, pour les observer en direct dans l'UI.
     */
    public LiveData<List<SurveyEntity>> getAllSurveysFromLocal() {
        return surveyDao.getAllSurveys();
    }

    /**
     * Fait un appel réseau pour récupérer la liste des sondages,
     * puis stocke le résultat en base (Room).
     */
    public void fetchSurveysFromApiAndStore(int sondeurId, String status) {
        apiService.getSondages(sondeurId, status).enqueue(new Callback<List<Survey>>() {
            @Override
            public void onResponse(Call<List<Survey>> call, Response<List<Survey>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Survey> surveys = response.body();
                } else {
                }
            }
            @Override
            public void onFailure(Call<List<Survey>> call, Throwable t) {
            }
        });
    }
}