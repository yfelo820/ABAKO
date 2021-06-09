package com.example.abako_bank.api.request.colector;

import com.example.abako_bank.models.Dispositivo;
import com.example.abako_bank.models.User;

public class ColectorConfig {

    private Dispositivo device;

    private User user;

    private CObjectOne config;

    public Dispositivo getDispositivo() {
        return device;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.device = dispositivo;
    }

    public User getUsuario() {
        return user;
    }

    public void setUsuario(User usuario) {
        this.user = usuario;
    }

    public CObjectOne getConfig() {
        return config;
    }

    public void setConfig(CObjectOne config) {
        this.config = config;
    }
}
