package com.example.abako_bank.api.response;

public class DefaultResponse {
    private String message;
    private String statusCode;

    private BankUser bankuser;

    public DefaultResponse(String message, String token) {
        this.message = message;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getStatus() {
        return statusCode;
    }

    public void setStatus(String status) {
        this.statusCode = status;
    }

    public BankUser getBankuser() {
        return bankuser;
    }

    public void setBankuser(BankUser bankuser) {
        this.bankuser = bankuser;
    }
}
