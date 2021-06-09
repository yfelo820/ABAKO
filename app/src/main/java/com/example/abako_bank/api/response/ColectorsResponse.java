package com.example.abako_bank.api.response;

import com.example.abako_bank.models.Colector;

import java.util.List;

public class ColectorsResponse {

    private String statusCode;
    private String message;
    private List<Colector> collectors;

    public ColectorsResponse() {
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
