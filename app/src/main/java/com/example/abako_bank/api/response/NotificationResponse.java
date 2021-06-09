package com.example.abako_bank.api.response;

import com.example.abako_bank.models.Notification;

import java.util.List;

public class NotificationResponse {
    private String message;
    private String statusCode;
    private List<Notification> notifications;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Notification> getData() {
        return notifications;
    }

    public void setData(List<Notification> data) {
        this.notifications = data;
    }

    public String getCode() {
        return statusCode;
    }

    public void setCode(String code) {
        this.statusCode = code;
    }
}
