package com.example.projet0406.api;

import android.content.Context;
import android.util.Log;

import com.example.projet0406.storage.SharedPrefManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.Request;

public class RetrofitClient {

    private static final String BASE_URL = "http://10.11.14.133:8000/";

    private static RetrofitClient instance;
    private final Retrofit retrofit;

    private RetrofitClient(Context context) {
        // 取出 token
        String token = SharedPrefManager.getInstance(context).getToken();

        // 日志拦截器（可选）
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 自定义拦截器，加上 token
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder()
                            .header("Authorization", "Bearer " + token)
                            .header("Accept", "application/json");
                    Request request = builder.build();
                    return chain.proceed(request);
                })
                .addInterceptor(loggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitClient(context);
        }
        return instance;
    }

    public ApiService getApi() {
        return retrofit.create(ApiService.class);
    }
}


