package com.example.abako_bank.models.config;

import com.orm.SugarRecord;

public class Config_Topes_Lim extends SugarRecord<Config_Topes_Lim> {

    private int tipojuego;

    private double tope_bola;

    private double tope_parlet;

    private double tope_centena;

    public double getTope_bola() {
        return tope_bola;
    }

    public void setTope_bola(double tope_bola) {
        this.tope_bola = tope_bola;
    }

    public double getTope_parlet() {
        return tope_parlet;
    }

    public void setTope_parlet(double tope_parlet) {
        this.tope_parlet = tope_parlet;
    }

    public double getTope_centena() {
        return tope_centena;
    }

    public void setTope_centena(double tope_centena) {
        this.tope_centena = tope_centena;
    }

    public int getTipo_juego() {
        return tipojuego;
    }

    public void setTipo_juego(int tipo_juego) {
        this.tipojuego = tipo_juego;
    }
}
