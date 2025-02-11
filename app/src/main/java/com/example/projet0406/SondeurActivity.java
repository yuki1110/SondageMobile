package com.example.projet0406;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet0406.api.ApiService;
import com.example.projet0406.api.RetrofitClient;
import com.example.projet0406.api.SondeurRequest;
import com.example.projet0406.models.Survey;
import com.example.projet0406.storage.SharedPrefManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SondeurActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SurveyAdapter surveyAdapter;
    private Button logoutButton;
    private List<Survey> surveyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sondeur);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        logoutButton = findViewById(R.id.logoutButton);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        surveyList = new ArrayList<>();
        surveyAdapter = new SurveyAdapter(surveyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(surveyAdapter);

        logoutButton.setOnClickListener(v -> {
            SharedPrefManager.getInstance(SondeurActivity.this).clear();
            Intent intent = new Intent(SondeurActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        chargerSondages();
    }

    private void chargerSondages() {
        progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = RetrofitClient.getInstance().getApi();
        int sondeurId = SharedPrefManager.getInstance(this).getSondeurId();

        Call<List<Survey>> call = apiService.getSurveys(new SondeurRequest(sondeurId));
        call.enqueue(new Callback<List<Survey>>() {
            @Override
            public void onResponse(Call<List<Survey>> call, Response<List<Survey>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    surveyList.clear();
                    surveyList.addAll(response.body());
                    surveyAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(SondeurActivity.this, "Aucun sondage trouvé", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Survey>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                if (t instanceof IOException) {
                    Toast.makeText(SondeurActivity.this, "Problème de connexion Internet", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SondeurActivity.this, "Erreur serveur. Réessayez plus tard.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}