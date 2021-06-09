package com.example.abako_bank.api;

import com.example.abako_bank.api.request.BankPhoneData;
import com.example.abako_bank.api.request.listero.ListerConfig;
import com.example.abako_bank.api.request.colector.ColectorConfig;
import com.example.abako_bank.api.response.AutenticateResponse;
import com.example.abako_bank.api.response.ColectorsResponse;
import com.example.abako_bank.api.response.DefaultResponse;
import com.example.abako_bank.api.response.PendingDeviceResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ConfiguracionService {


    @Headers("Content-Type: application/json")
    @POST("api/bank/device/aprove/")
    Call<DefaultResponse> crearConfigListero(@Body ListerConfig listerConfig, @Header("access-token") String token, @Header("device") String device);

    @Headers("Content-Type: application/json")
    @POST("api/bank/device/aprove/")
    Call<DefaultResponse> crearConfigColector(@Body ColectorConfig listerConfig, @Header("access-token") String token, @Header("device") String device);

    @POST("api/bank/autentication")
    Call<AutenticateResponse> autenticateBank(@Body BankPhoneData bankPhoneData);

    @Headers("Content-Type: application/json")
    @GET("api/bank/pendingdevices")
    Call<PendingDeviceResponse> dispositivosPendientes(@Header("access-token") String token, @Header("device") String device);

    @Headers("Content-Type: application/json")
    @GET("/api/bank/collectors/")
    Call<ColectorsResponse> todosColectores(@Header("access-token") String token, @Header("device") String device);
}
