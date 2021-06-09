package com.example.abako_bank.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.abako_bank.api.repository.BankRepository;
import com.example.abako_bank.api.request.BankPhoneData;
import com.example.abako_bank.api.response.AutenticateResponse;
import com.example.abako_bank.api.response.DefaultResponse;
import com.example.abako_bank.api.response.TiroResponse;
import com.example.abako_bank.api.response.prizes.PrizesResponse;
import com.example.abako_bank.models.Settings;
import com.example.abako_bank.models.Tiro;
import com.example.abako_bank.websocket.utils.DebugUtils;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetViewModel extends ViewModel {

    private BankRepository bankRepository;
    private MutableLiveData<TiroResponse> liveData;
    private MutableLiveData<DefaultResponse> defaultResponseMutableLiveData;


    BottomSheetViewModel(BankRepository bankRepository){
        this.bankRepository = bankRepository;
        liveData = new MutableLiveData<>();
        defaultResponseMutableLiveData = new MutableLiveData<>();


    }

    public void getPicks(final String fecha_desde, final String fecha_hasta, final boolean estado){
        bankRepository.getPicks(fecha_desde, fecha_hasta, estado).enqueue(new Callback<TiroResponse>() {
            @Override
            public void onResponse(Call<TiroResponse> call, Response<TiroResponse > response) {
                if(response.code() == 200){
                    TiroResponse defaultResponse = response.body();
                    liveData.postValue(defaultResponse);
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
                                getPicks(fecha_desde, fecha_hasta, estado);
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
            public void onFailure(Call<TiroResponse> call, Throwable t) {
                DefaultResponse defaultResponse = new DefaultResponse("Error de conexión", "");
                defaultResponse.setStatus("400");
                defaultResponseMutableLiveData.postValue(defaultResponse);
            }
        });
    }


    public MutableLiveData<TiroResponse> getLiveData() {
        return liveData;
    }



    public MutableLiveData<DefaultResponse> getDefaultResponseMutableLiveData() {
        return defaultResponseMutableLiveData;
    }

}
