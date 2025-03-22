package com.example.projet0406.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
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
        apiService = RetrofitClient.getInstance(context).getApi();}

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
            public void onResponse(@NonNull Call<List<Survey>> call, @NonNull Response<List<Survey>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Survey> surveys = response.body();
                    storeSurveys(surveys);
                } else {
                    Log.e("SurveyRepository", "Erreur lors de la récupération des sondages");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Survey>> call, @NonNull Throwable t) {
                Log.e("SurveyRepository", "Échec de la connexion au serveur", t);
            }
        });
    }

    public void storeSurveys(List<Survey> surveys) {
        // Stocker les sondages dans la base de données locale
        // Cette méthode doit être exécutée dans un thread séparé
        new Thread(() -> {
            // Conversion et stockage des sondages
            List<SurveyEntity> surveyEntities = new ArrayList<>();
            for (Survey survey : surveys) {
                SurveyEntity entity = new SurveyEntity(survey.getIdSurvey(), survey.getTitle(), survey.getDescription());
                surveyEntities.add(entity);
            }
            surveyDao.insertAll(surveyEntities);
        }).start();
    }
}