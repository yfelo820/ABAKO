package com.example.abako_bank.api.response;

import com.example.abako_bank.models.PendingDevice;

import java.util.List;

public class PendingDeviceResponse {
    private String statusCode;
    private String message;

    private List<PendingDevice> devices;

    public PendingDeviceResponse() {
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PendingDevice> getDevices() {
        return devices;
    }

    public void setDevices(List<PendingDevice> devices) {
        this.devices = devices;
    }
}
