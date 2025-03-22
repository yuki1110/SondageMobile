package com.example.projet0406.api;

import android.content.Context;

import com.example.projet0406.storage.SharedPrefManager;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // Exemple : si ton Laravel tourne en local sur 10.0.2.2:8000 (émulateur Android) ou 192.168.x.x
    // Remplace par l’URL adaptée à ton environnement.
    private static final String BASE_URL = "http://10.11.14.133/";

    private static RetrofitClient instance;
    private final ApiService api;

    private RetrofitClient(Context context) {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    String token = SharedPrefManager.getInstance(context).getToken();
                    Request modified = original.newBuilder()
                            .header("Authorization", "Bearer " + token)
                            .build();
                    return chain.proceed(modified);
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ApiService.class);
    }

    public static synchronized RetrofitClient getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitClient(context.getApplicationContext());
        }
        return instance;
    }

    public ApiService getApi() {
        return api;
    }
}