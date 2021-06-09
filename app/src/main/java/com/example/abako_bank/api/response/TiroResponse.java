package com.example.abako_bank.api.response;

import com.example.abako_bank.models.Tiro;

import java.util.List;

public class TiroResponse {

    private String statusCode;
    private String message;
    private List<Tiro> picks;

    public TiroResponse() {
    }

    public TiroResponse(String statusCode, String message, List<Tiro> picks) {
        this.statusCode = statusCode;
        this.message = message;
        this.picks = picks;
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

    public List<Tiro> getPicks() {
        return picks;
    }

    public void setPicks(List<Tiro> picks) {
        this.picks = picks;
    }
}
