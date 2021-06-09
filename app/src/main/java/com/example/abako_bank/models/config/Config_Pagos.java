package com.example.abako_bank.models.config;

import com.orm.SugarRecord;

public class Config_Pagos extends SugarRecord<Config_Pagos> {

    private int tipojuego;//bote charada

    private double fijo;

    private double corrido;

    private double parlet;

    private double parlet_m;

    private double centena;

    public Config_Pagos() {
    }

    public Config_Pagos(double fijo, double corrido, double parlet, double parlet_m, double centena, int tipo_juego) {
        this.fijo = fijo;
        this.corrido = corrido;
        this.parlet = parlet;
        this.parlet_m = parlet_m;
        this.centena = centena;
        this.tipojuego = tipo_juego;
    }

    public double getFijo() {
        return fijo;
    }

    public void setFijo(double fijo) {
        this.fijo = fijo;
    }

    public double getCorrido() {
        return corrido;
    }

    public void setCorrido(double corrido) {
        this.corrido = corrido;
    }

    public double getParlet() {
        return parlet;
    }

    public void setParlet(double parlet) {
        this.parlet = parlet;
    }

    public double getParlet_m() {
        return parlet_m;
    }

    public void setParlet_m(double parlet_m) {
        this.parlet_m = parlet_m;
    }

    public double getCentena() {
        return centena;
    }

    public void setCentena(double centena) {
        this.centena = centena;
    }

    public int getTipo_juego() {
        return tipojuego;
    }

    public void setTipo_juego(int tipo_juego) {
        this.tipojuego = tipo_juego;
    }
}
