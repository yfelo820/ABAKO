package com.example.abako_bank.api.response.prizes;

import com.example.abako_bank.models.Colector;

import java.util.List;

public class PrizesColectorResponse {

    private String statusCode;
    private Colector collector;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Colector getCollector() {
        return collector;
    }

    public void setCollector(Colector collector) {
        this.collector = collector;
    }
}
