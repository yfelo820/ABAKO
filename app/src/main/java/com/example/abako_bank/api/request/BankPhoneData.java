package com.example.abako_bank.api.request;

public class BankPhoneData {
    private String username;

    private String password;

    private String device;

    private String imei;

    public BankPhoneData() {
    }

    public BankPhoneData(String username, String password, String device, String imei) {
        this.username = username;
        this.password = password;
        this.device = device;
        this.imei = imei;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid_dispositivo() {
        return device;
    }

    public void setUid_dispositivo(String uid_dispositivo) {
        this.device = uid_dispositivo;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
