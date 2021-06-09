package com.example.abako_bank.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.abako_bank.api.repository.BankRepository;
import com.example.abako_bank.api.response.AutenticateResponse;
import com.example.abako_bank.api.response.BlackListResponse;
import com.example.abako_bank.api.response.DefaultResponse;
import com.example.abako_bank.api.response.NotificationResponse;
import com.example.abako_bank.api.response.PendingDeviceResponse;
import com.example.abako_bank.models.Dispositivo;
import com.example.abako_bank.models.Notification;
import com.example.abako_bank.models.Settings;
import com.example.abako_bank.websocket.SocketLiveData;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationViewModel extends ViewModel {

    private LiveData<DefaultResponse> defaultResponseLiveData;

    private LiveData<AutenticateResponse> autenticateResponseLiveData;

    private LiveData<NotificationResponse> listLiveData;

    private MutableLiveData<DefaultResponse> updateNotificacionLiveData = new MutableLiveData<>();



    private BankRepository bankRepository;

    private LiveData<BlackListResponse> blackListResponseLiveData;

    NotificationViewModel(BankRepository bankRepository){
        this.bankRepository = bankRepository;
        defaultResponseLiveData = bankRepository.getDefaultResponseMutableLiveData();
        autenticateResponseLiveData = bankRepository.getAutenticateResponseMutableLiveData();
        listLiveData = bankRepository.getListMutableLiveData();
        blackListResponseLiveData = bankRepository.getBlackListResponseMutableLiveData();
    }

    public LiveData<DefaultResponse> getDefaultResponseLiveData() {
        return defaultResponseLiveData;
    }

    public void setDefaultResponseLiveData(LiveData<DefaultResponse> defaultResponseLiveData) {
        this.defaultResponseLiveData = defaultResponseLiveData;
    }

    public LiveData<NotificationResponse> getListLiveData() {
        return listLiveData;
    }

    public void setListLiveData(LiveData<NotificationResponse> listLiveData) {
        this.listLiveData = listLiveData;
    }

    public BankRepository getBankRepository() {
        return bankRepository;
    }

    public void setBankRepository(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public LiveData<AutenticateResponse> getAutenticateResponseLiveData() {
        return autenticateResponseLiveData;
    }

    public void setAutenticateResponseLiveData(LiveData<AutenticateResponse> autenticateResponseLiveData) {
        this.autenticateResponseLiveData = autenticateResponseLiveData;
    }

    public LiveData<BlackListResponse> getBlackListResponseLiveData() {
        return blackListResponseLiveData;
    }

    public void updateNotificacion(Integer id){
        bankRepository.actualizarEstadoNotificacion(id).enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code() == 200){
                    DefaultResponse defaultResponse = new DefaultResponse("Actualizada", "");
                    defaultResponse.setStatus("200");
                    updateNotificacionLiveData.postValue(defaultResponse);
                }else if(response.code() == 400){
                    try {
                        String res = response.errorBody().string();
                        Gson g = new Gson();
                        DefaultResponse defaultResponse = g.fromJson(res, DefaultResponse.class);
                        defaultResponse.setStatus("400");
                        updateNotificacionLiveData.postValue(defaultResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                DefaultResponse defaultResponse = new DefaultResponse("Error", "");
                defaultResponse.setStatus("400");
                updateNotificacionLiveData.postValue(defaultResponse);
            }
        });
    }

    public MutableLiveData<DefaultResponse> getUpdateNotificacionLiveData() {
        return updateNotificacionLiveData;
    }

}
