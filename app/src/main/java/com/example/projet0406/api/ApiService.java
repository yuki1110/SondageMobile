package com.example.projet0406.api;

import com.example.projet0406.api.dto.LoginRequest;
import com.example.projet0406.api.dto.SondeurAssignationRequest;
import com.example.projet0406.api.dto.ResponseRequest;
import com.example.projet0406.models.Survey;
import com.example.projet0406.models.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @POST("login")
    Call<UserResponse> login(@Body LoginRequest request);

    @POST("logout")
    Call<Void> logout();

    @GET("sondages")
    Call<List<Survey>> getSondages(
            @Query("user_id") int userId,
            @Query("status") String status
    );

    @POST("reponses")
    Call<Void> envoyerReponse(@Body ResponseRequest request);

}