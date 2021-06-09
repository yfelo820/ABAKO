package com.example.abako_bank.api.request.listero;

import com.example.abako_bank.models.Dispositivo;
import com.example.abako_bank.models.User;

public class ListerConfig {

    private Dispositivo device;

    private User user;

    private Config config;

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

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
