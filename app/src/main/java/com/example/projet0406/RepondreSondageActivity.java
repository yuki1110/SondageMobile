package com.example.projet0406;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet0406.api.ApiService;
import com.example.projet0406.api.RetrofitClient;
import com.example.projet0406.api.dto.ResponseRequest;
import com.example.projet0406.room.AnswerEntity;
import com.example.projet0406.room.AsklyDatabase;
import com.example.projet0406.room.DatabaseClient;
import com.example.projet0406.storage.SharedPrefManager;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepondreSondageActivity extends AppCompatActivity {

    private TextView titreSondage, descriptionSondage;
    private EditText reponseInput;
    private Button envoyerReponseButton;
    private ProgressBar progressBar;
    private int sondageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repondre_sondage);

        titreSondage = findViewById(R.id.titreSondage);
        descriptionSondage = findViewById(R.id.descriptionSondage);
        reponseInput = findViewById(R.id.reponseInput);
        envoyerReponseButton = findViewById(R.id.envoyerReponseButton);
        progressBar = findViewById(R.id.progressBar);

        // Récupère les infos du sondage sélectionné
        sondageId = getIntent().getIntExtra("sondage_id", -1);
        String titre = getIntent().getStringExtra("sondage_titre");
        String description = getIntent().getStringExtra("sondage_description");

        titreSondage.setText(titre);
        descriptionSondage.setText(description);

        envoyerReponseButton.setOnClickListener(v -> envoyerReponse());
    }

    private void envoyerReponse() {
        String reponse = reponseInput.getText().toString().trim();
        if (reponse.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer une réponse", Toast.LENGTH_SHORT).show();
            return;
        }

        int userId = SharedPrefManager.getInstance(this).getIdUser();
        ApiService apiService = RetrofitClient.getInstance(this).getApi();

        // On crée l'entité locale
        AnswerEntity answerEntity = new AnswerEntity(userId, sondageId, reponse, false);

        // Prépare la requête : liste de réponses avec un seul élément
        ResponseRequest request = new ResponseRequest(
                userId,
                sondageId,
                Collections.singletonList(reponse)
        );

        progressBar.setVisibility(View.VISIBLE);

        Call<Void> call = apiService.envoyerReponse(sondageId, request);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Toast.makeText(RepondreSondageActivity.this, "Réponse envoyée", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RepondreSondageActivity.this, "Échec de l'envoi, stocké hors-ligne", Toast.LENGTH_SHORT).show();
                    stockerHorsLigne(answerEntity);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(RepondreSondageActivity.this, "Pas de réseau, réponse stockée", Toast.LENGTH_SHORT).show();
                stockerHorsLigne(answerEntity);
            }
        });
    }

    private void stockerHorsLigne(AnswerEntity answerEntity) {
        new Thread(() -> {
            AsklyDatabase db = DatabaseClient.getInstance(RepondreSondageActivity.this);
            db.answerDao().insertAnswer(answerEntity);
        }).start();
        finish();
    }
}