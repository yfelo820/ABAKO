package com.example.abako_bank.api.response.prizes;

import com.example.abako_bank.models.Colector;

import java.util.List;

public class PrizesResponse {

    private String statusCode;
    private String message;
    private List<Colector> collectors;

    public PrizesResponse(String statusCode, String message, List<Colector> collectors) {
        this.statusCode = statusCode;
        this.message = message;
        this.collectors = collectors;
    }

    public PrizesResponse() {
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

    public List<Colector> getCollectors() {
        return collectors;
    }

    public void setCollectors(List<Colector> collectors) {
        this.collectors = collectors;
    }


}
