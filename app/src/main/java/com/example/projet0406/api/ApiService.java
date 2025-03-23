package com.example.projet0406.api;

import com.example.projet0406.api.dto.LoginRequest;
import com.example.projet0406.api.dto.SondeurAssignationRequest;
import com.example.projet0406.api.dto.ResponseRequest;
import com.example.projet0406.models.Survey;
import com.example.projet0406.models.SurveyResponse;
import com.example.projet0406.models.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("/api/login")
    Call<UserResponse> login(@Body LoginRequest loginRequest);

    @POST("/api/logout")
    Call<Void> logout();

    @GET("/api/sondeur/sondages")
    Call<SurveyResponse> getSondagesWithToken(@Header("Authorization") String bearerToken);

    @GET("sondages")
    Call<List<Survey>> getSondages(
            @Query("user_id") int userId,
            @Query("status") String status
    );

    @POST("sondeur/sondage/{id}/repondre")
    Call<Void> envoyerReponse(@Path("id") int sondageId, @Body ResponseRequest body);

    @GET("api/user/{id}")
    Call<UserResponse> getUser(@Path("id") int userId);
}
