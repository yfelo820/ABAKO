package com.example.abako_bank.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.abako_bank.api.repository.BankRepository;
import com.example.abako_bank.api.response.AutenticateResponse;
import com.example.abako_bank.api.response.DefaultResponse;
import com.example.abako_bank.api.response.prizes.PrizesListasResponse;
import com.example.abako_bank.api.response.prizes.PrizesColectorResponse;
import com.example.abako_bank.api.response.prizes.PrizesResponse;
import com.example.abako_bank.models.Settings;
import com.example.abako_bank.websocket.utils.DebugUtils;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListasViewModel extends ViewModel {

    private BankRepository bankRepository;
    private MutableLiveData<PrizesListasResponse> listasResponseMutableLiveData;
    private MutableLiveData<DefaultResponse> defaultResponseMutableLiveData;

    ListasViewModel(BankRepository bankRepository){
        this.bankRepository = bankRepository;
        listasResponseMutableLiveData = new MutableLiveData<>();
        defaultResponseMutableLiveData = new MutableLiveData<>();

    }

    public void getPrizeListasByLister(final String lister_id, final String tiro_id){
        bankRepository.getPrizeListasByLister(lister_id, tiro_id).enqueue(new Callback<PrizesListasResponse>() {
            @Override
            public void onResponse(Call<PrizesListasResponse> call, Response<PrizesListasResponse> response) {
                if(response.code() == 200){
                    PrizesListasResponse defaultResponse = response.body();
                    listasResponseMutableLiveData.postValue(defaultResponse);
                }
                if(response.code() == 400){
                    try {
                        String res = response.errorBody().string();
                        Gson g = new Gson();
                        DefaultResponse defaultResponse = g.fromJson(res, DefaultResponse.class);
                        defaultResponseMutableLiveData.postValue(defaultResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(response.code() == 401){
                    bankRepository.autenticatBankSecondMethod().enqueue(new Callback<AutenticateResponse>() {
                        @Override
                        public void onResponse(Call<AutenticateResponse> call, Response<AutenticateResponse> response) {
                            if(response.code() == 200){
                                AutenticateResponse defaultResponse = response.body();
                                defaultResponse.setStatus("200");
                                //guardar el token del banco
                                DebugUtils.debug(BankRepository.class, defaultResponse.getToken());
                                if(!Settings.listAll(Settings.class).isEmpty()){
                                    Settings settings = Settings.listAll(Settings.class).get(0);
                                    settings.setToken(defaultResponse.getToken());
                                    settings.save();
                                }
                                getPrizeListasByLister(lister_id, tiro_id);
                            }
                            if(response.code() == 400){
                                try {
                                    String res = response.errorBody().string();
                                    Gson g = new Gson();
                                    DefaultResponse defaultResponse = g.fromJson(res, DefaultResponse.class);
                                    defaultResponseMutableLiveData.postValue(defaultResponse);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<AutenticateResponse> call, Throwable t) {
                            DefaultResponse defaultResponse = new DefaultResponse("Error de conexión", "");
                            defaultResponse.setStatus("400");
                            defaultResponseMutableLiveData.postValue(defaultResponse);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<PrizesListasResponse> call, Throwable t) {
                DefaultResponse defaultResponse = new DefaultResponse("Error de conexión", "");
                defaultResponse.setStatus("400");
                defaultResponseMutableLiveData.postValue(defaultResponse);
            }
        });
    }

    public MutableLiveData<PrizesListasResponse> getListasResponseMutableLiveData() {
        return listasResponseMutableLiveData;
    }

    public void setListasResponseMutableLiveData(MutableLiveData<PrizesListasResponse> listasResponseMutableLiveData) {
        this.listasResponseMutableLiveData = listasResponseMutableLiveData;
    }

    public MutableLiveData<DefaultResponse> getDefaultResponseMutableLiveData() {
        return defaultResponseMutableLiveData;
    }

    public void setDefaultResponseMutableLiveData(MutableLiveData<DefaultResponse> defaultResponseMutableLiveData) {
        this.defaultResponseMutableLiveData = defaultResponseMutableLiveData;
    }
}
