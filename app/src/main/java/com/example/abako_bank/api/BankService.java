package com.example.abako_bank.api;

import com.example.abako_bank.api.request.BankPhoneData;
import com.example.abako_bank.api.request.BlackList;
import com.example.abako_bank.api.request.LimitadosRequest;
import com.example.abako_bank.api.request.NoJueganRequest;
import com.example.abako_bank.api.request.TiroRequest;
import com.example.abako_bank.api.response.AutenticateResponse;
import com.example.abako_bank.api.response.BlackListResponse;
import com.example.abako_bank.api.response.DefaultResponse;
import com.example.abako_bank.api.response.NotificationResponse;
import com.example.abako_bank.api.response.TiroResponse;
import com.example.abako_bank.api.response.prizes.PrizesColectorResponse;
import com.example.abako_bank.api.response.prizes.PrizesListasResponse;
import com.example.abako_bank.api.response.prizes.PrizesResponse;
import com.example.abako_bank.models.Tiro;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface BankService {

    @POST("api/bank/create")
    Call<DefaultResponse> createBank(@Body BankPhoneData bankPhoneData);

    @POST("api/bank/autentication")
    Call<AutenticateResponse> autenticateBank(@Body BankPhoneData bankPhoneData);


    //@Headers({"Content-Type", "application/json"})
    @Headers("Content-Type: application/json")
    @GET("api/bank/notifications/")
    Call<NotificationResponse> obtenerNotificaciones(@Header("access-token") String token, @Header("device") String device,
                                                     @Query("estado") String estado, @Query("desde") String desde, @Query("hasta") String hasta);

    @Headers("Content-Type: application/json")
    @POST("api/bank/device/blacklist/add")
    Call<BlackListResponse> adicionarDispABlackList(@Header("access-token") String token, @Header("device") String device, @Body BlackList blackList);

    @Headers("Content-Type: application/json")
    @PUT("api/bank/notification/update/")
    Call<DefaultResponse> actualizarNotificacion(@Header("access-token") String token, @Header("device") String device, @Query("id") Integer id);

    @Headers("Content-Type: application/json")
    @POST("api/bank/limited/")
    Call<DefaultResponse> sendLimitados(@Header("access-token") String token, @Header("device") String device, @Body LimitadosRequest limitadosRequest);

    @Headers("Content-Type: application/json")
    @POST("api/bank/notplay/")
    Call<DefaultResponse> sendNoJuegan(@Header("access-token") String token, @Header("device") String device, @Body NoJueganRequest noJueganRequest);

    @Headers("Content-Type: application/json")
    @GET("api/prizes/collectors")
    Call<PrizesResponse> getPrizes(@Header("access-token") String token, @Header("device") String device, @Query("tiro") String tiro);

    @Headers("Content-Type: application/json")
    @GET("api/picks")
    Call<TiroResponse> getPicks(@Header("access-token") String token, @Header("device") String device, @Query("desde") String desde, @Query("hasta") String hasta,
                                @Query("estado") boolean estado);

    @Headers("Content-Type: application/json")
    @GET("/api/prizes/collector/")
    Call<PrizesColectorResponse> getPrizeByColector(@Header("access-token") String token, @Header("device") String device, @Query("colector") String colector, @Query("tiro") String tiro);

    @Headers("Content-Type: application/json")
    @GET("/api/prizes/lister/")
    Call<PrizesListasResponse> getPrizeListasByLister(@Header("access-token") String token, @Header("device") String device, @Query("listero") String colector, @Query("tiro") String tiro);

    @Headers("Content-Type: application/json")
    @POST("api/bank/pick/add")
    Call<DefaultResponse> setPick(@Header("access-token") String token, @Header("device") String device, @Body TiroRequest tiro);



}
