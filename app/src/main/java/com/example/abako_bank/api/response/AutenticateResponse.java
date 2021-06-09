package com.example.abako_bank.api.response;

public class AutenticateResponse {

    private String token;

    private String message;

    private String statusCode;

    public AutenticateResponse() {
    }

    public AutenticateResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return statusCode;
    }

    public void setStatus(String status) {
        this.statusCode = status;
    }
}
