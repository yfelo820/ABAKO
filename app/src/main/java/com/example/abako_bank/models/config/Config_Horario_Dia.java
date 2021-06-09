package com.example.abako_bank.models.config;

import com.orm.SugarRecord;

public class Config_Horario_Dia extends SugarRecord<Config_Horario_Dia> {

    private int tipojuego;

    private String hora_activo;

    private String minuto_activo;

    private String hora_inactivo;

    private String minuto_inactivo;

    public Config_Horario_Dia(String hora_activo, String minuto_activo, String hora_inactivo, String minuto_inactivo,
                              int tipo_juego) {
        this.hora_activo = hora_activo;
        this.minuto_activo = minuto_activo;
        this.hora_inactivo = hora_inactivo;
        this.minuto_inactivo = minuto_inactivo;
        this.tipojuego = tipo_juego;
    }

    public Config_Horario_Dia() {
    }

    public String getHora_activo() {
        return hora_activo;
    }

    public void setHora_activo(String hora_activo) {
        this.hora_activo = hora_activo;
    }

    public String getMinuto_activo() {
        return minuto_activo;
    }

    public void setMinuto_activo(String minuto_activo) {
        this.minuto_activo = minuto_activo;
    }

    public String getHora_inactivo() {
        return hora_inactivo;
    }

    public void setHora_inactivo(String hora_inactivo) {
        this.hora_inactivo = hora_inactivo;
    }

    public String getMinuto_inactivo() {
        return minuto_inactivo;
    }

    public void setMinuto_inactivo(String minuto_inactivo) {
        this.minuto_inactivo = minuto_inactivo;
    }

    public int getTipo_juego() {
        return tipojuego;
    }

    public void setTipo_juego(int tipo_juego) {
        this.tipojuego = tipo_juego;
    }
}
