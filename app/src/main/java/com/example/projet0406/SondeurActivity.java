package com.example.projet0406;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet0406.models.Survey;
import com.example.projet0406.repository.SurveyRepository;
import com.example.projet0406.room.SurveyEntity;
import com.example.projet0406.storage.SharedPrefManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class SondeurActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SurveyAdapter surveyAdapter;
    private final List<Survey> surveyList = new ArrayList<>();
    private List<SurveyEntity> oldCacheList;
    private SurveyRepository surveyRepository;
    private MaterialToolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private static final String CHANNEL_ID = "askly_channel_id";

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

        createNotificationChannel();

        drawerLayout = findViewById(R.id.drawerLayout);
        navView = findViewById(R.id.navView);
        toolbar = findViewById(R.id.sondeurToolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
                drawerLayout.closeDrawers();
                return true;
            } else if (id == R.id.nav_logout) {
                SharedPrefManager.getInstance(this).clear();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            }
            return false;
        });

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        surveyAdapter = new SurveyAdapter(surveyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(surveyAdapter);

        surveyRepository = new SurveyRepository(this);
        surveyRepository.getAllSurveysFromLocal().observe(this, newList -> {
            int nbNouveaux = detecterNouveauxSondages(newList, oldCacheList);
            surveyList.clear();
            for (SurveyEntity se : newList) {
                Survey s = new Survey();
                s.setIdSurvey(se.idSurvey);
                s.setTitle(se.title);
                s.setDescription(se.description);
                surveyList.add(s);
            }
            surveyAdapter.notifyDataSetChanged();
            if (nbNouveaux > 0) envoyerNotificationNouveauSondage(nbNouveaux);
            oldCacheList = newList;
        });

        chargerSondages();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "Askly Notifications";
            String desc = "Notifications pour l'application Askly";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(desc);
            NotificationManager mgr = getSystemService(NotificationManager.class);
            if (mgr != null) mgr.createNotificationChannel(channel);
        }
    }

    private void chargerSondages() {
        progressBar.setVisibility(View.VISIBLE);
        int sondeurId = SharedPrefManager.getInstance(this).getIdUser();
        surveyRepository.fetchSurveysFromApiAndStore(sondeurId, "en_cours");
        progressBar.setVisibility(View.GONE);
    }

    private int detecterNouveauxSondages(List<SurveyEntity> newList, List<SurveyEntity> oldList) {
        if (oldList == null || oldList.isEmpty()) return (newList == null) ? 0 : newList.size();
        int count = 0;
        for (SurveyEntity newItem : newList) {
            boolean existeDeja = false;
            for (SurveyEntity oldItem : oldList) {
                if (newItem.idSurvey == oldItem.idSurvey) {
                    existeDeja = true;
                    break;
                }
            }
            if (!existeDeja) count++;
        }
        return count;
    }

    private void envoyerNotificationNouveauSondage(int nbNouveaux) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Nouveau(x) Sondage(s)")
                .setContentText(nbNouveaux + " sondage(s) ajouté(s) !")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat mgr = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) return;
        mgr.notify(1001, builder.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sondeur, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_logout) {
            SharedPrefManager.getInstance(this).clear();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}