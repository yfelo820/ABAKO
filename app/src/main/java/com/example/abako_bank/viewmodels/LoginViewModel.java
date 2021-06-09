package com.example.abako_bank.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.abako_bank.api.repository.BankRepository;
import com.example.abako_bank.api.response.AutenticateResponse;
import com.example.abako_bank.api.response.DefaultResponse;
import com.example.abako_bank.api.response.NotificationResponse;
import com.example.abako_bank.models.Settings;
import com.example.abako_bank.websocket.SocketLiveData;

import java.util.Set;

public class LoginViewModel extends ViewModel {
    private LiveData<String> username;

    private LiveData<String> password;

    private LiveData<String> uuid;

    private LiveData<String> imei;

    private LiveData<DefaultResponse> defaultResponseLiveData;

    private LiveData<AutenticateResponse> autenticateResponseLiveData;

    private BankRepository bankRepository;

    private Settings settings;

    private SocketLiveData socketLiveData;

    private LiveData<NotificationResponse> listLiveData;

    LoginViewModel(BankRepository bankRepository){
        this.bankRepository = bankRepository;
        defaultResponseLiveData = bankRepository.getDefaultResponseMutableLiveData();
        autenticateResponseLiveData = bankRepository.getAutenticateResponseMutableLiveData();
        socketLiveData = SocketLiveData.get();
        listLiveData = bankRepository.getListMutableLiveData();
    }

    public void createBank(String username, String password, String uuid, String imei) {
        // can be launched in a separate asynchronous job
        bankRepository.createBank(username, password, uuid, imei);
    }

    public void autenticateBank(String username, String password, String uuid, String imei) {
        // can be launched in a separate asynchronous job
        bankRepository.autenticateBank(username, password, uuid, imei);
    }

    public boolean isCreated(){
        if(Settings.listAll(Settings.class).isEmpty()){
            return false;
        }
        return true;
    }

    public void saveSettings(String username, String password, String uuid, String imei){
        Settings settings = new Settings();
        settings.setDevice_id(imei);
        settings.setDevice_uuid(uuid);
        settings.setUsername(username);
        settings.setPassword(password);
        settings.save();
    }

    public Settings getSetting(){
        return  Settings.listAll(Settings.class).get(0);
    }

    public LiveData<DefaultResponse> getDefaultResponseLiveData() {
        return defaultResponseLiveData;
    }

    public SocketLiveData getSocketLiveData() {
        return socketLiveData;
    }

    public LiveData<AutenticateResponse> getAutenticateResponseLiveData() {
        return autenticateResponseLiveData;
    }

    public BankRepository getBankRepository() {
        return bankRepository;
    }

    public LiveData<NotificationResponse> getListLiveData() {
        return listLiveData;
    }
}
