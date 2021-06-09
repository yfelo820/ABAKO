package com.example.abako_bank.api.response.prizes;

import com.example.abako_bank.models.Listero;

public class PrizesListasResponse {

    private String statusCode;

    private Listero lister;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Listero getLister() {
        return lister;
    }

    public void setLister(Listero lister) {
        this.lister = lister;
    }
}
