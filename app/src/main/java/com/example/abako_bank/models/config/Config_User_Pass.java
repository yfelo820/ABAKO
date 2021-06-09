package com.example.abako_bank.models.config;

import com.orm.SugarRecord;

public class Config_User_Pass extends SugarRecord<Config_User_Pass> {

    private String username;

    private String password;

    private String nombre;

    private String colector_nombre;

    private String device;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getColector_nombre() {
        return colector_nombre;
    }

    public void setColector_nombre(String colector_nombre) {
        this.colector_nombre = colector_nombre;
    }
}
