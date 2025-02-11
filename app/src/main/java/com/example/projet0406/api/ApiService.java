package com.example.projet0406.api;

import com.example.projet0406.models.Survey;
import com.example.projet0406.models.ResponseRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login")
    Call<UserResponse> login(@Body LoginRequest request);

    @POST("get-surveys")  //exemple
    Call<List<Survey>> getSurveys(@Body SondeurRequest request);

    @POST("envoyer-reponse")  //mettre bon endpoint laravel
    Call<Void> envoyerReponse(@Body ResponseRequest request);
}