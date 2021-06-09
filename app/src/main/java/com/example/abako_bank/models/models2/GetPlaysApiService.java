package com.example.abako_bank.models.models2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetPlaysApiService {


    @Headers("Content-Type: application/json")
    @GET("plays/pag")
    Call<PlaysResponse> GetPlays(@Header("access-token") String token, @Header("device") String device , @Query("fecha") String fecha, @Query("page") int page, @Query("size") int size );



}
