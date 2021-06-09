package com.example.abako_bank.api.response;

public class BlackListResponse {
    private String message;

    private int code;

    private Object blacklistrecord;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
