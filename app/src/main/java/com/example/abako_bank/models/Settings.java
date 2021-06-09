package com.example.abako_bank.models;

import com.orm.SugarRecord;

public class Settings extends SugarRecord<Settings> {

    private String username;

    private String password;

    private String token;

    private String device_id;

    private String device_uuid;

    public Settings() {
    }

    public Settings(String username, String password, String token, String device_id, String device_uuid) {
        this.username = username;
        this.password = password;
        this.token = token;
        this.device_id = device_id;
        this.device_uuid = device_uuid;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_uuid() {
        return device_uuid;
    }

    public void setDevice_uuid(String device_uuid) {
        this.device_uuid = device_uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
