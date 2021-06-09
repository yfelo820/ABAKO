package com.example.abako_bank.api.request;

public class BlackList {
    String device;
    String imei;

    public BlackList() {
    }

    public BlackList(String device, String imei) {
        this.device = device;
        this.imei = imei;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
