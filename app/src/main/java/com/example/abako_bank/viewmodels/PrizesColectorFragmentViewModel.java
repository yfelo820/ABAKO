package com.example.abako_bank.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.abako_bank.api.repository.BankRepository;
import com.example.abako_bank.api.response.DefaultResponse;
import com.example.abako_bank.api.response.prizes.PrizesColectorResponse;
import com.example.abako_bank.api.response.prizes.PrizesResponse;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrizesColectorFragmentViewModel extends ViewModel {

    private BankRepository bankRepository;
    private MutableLiveData<PrizesColectorResponse> prizesColectorResponseMutableLiveData;
    private MutableLiveData<DefaultResponse> defaultResponseMutableLiveData;

    PrizesColectorFragmentViewModel(BankRepository bankRepository){
        this.bankRepository = bankRepository;
        defaultResponseMutableLiveData = new MutableLiveData<>();
        prizesColectorResponseMutableLiveData = new MutableLiveData<>();

    }

    public void getPrizeByColector(String colector_id, String tiro_id){
        bankRepository.getPrizeByColector(colector_id, tiro_id).enqueue(new Callback<PrizesColectorResponse>() {
            @Override
            public void onResponse(Call<PrizesColectorResponse> call, Response<PrizesColectorResponse> response) {
                if(response.code() == 200){
                    PrizesColectorResponse defaultResponse = response.body();
                    prizesColectorResponseMutableLiveData.postValue(defaultResponse);
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
            public void onFailure(Call<PrizesColectorResponse> call, Throwable t) {
                DefaultResponse defaultResponse = new DefaultResponse("Error de conexión", "");
                defaultResponse.setStatus("400");
                defaultResponseMutableLiveData.postValue(defaultResponse);
            }
        });
    }

    public MutableLiveData<PrizesColectorResponse> getPrizesColectorResponseMutableLiveData() {
        return prizesColectorResponseMutableLiveData;
    }

    public MutableLiveData<DefaultResponse> getDefaultResponseMutableLiveData() {
        return defaultResponseMutableLiveData;
    }
}
