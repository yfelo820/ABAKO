package com.example.abako_bank.models.config;

import com.orm.SugarRecord;

public class Config_Pagos_Limitados extends SugarRecord<Config_Pagos_Limitados> {

    private int tipojuego;

    private double fijo;

    private double corrido;

    private double parlet;

    public Config_Pagos_Limitados() {
    }

    public Config_Pagos_Limitados(int tipo_juego, double fijo, double corrido, double parlet) {
        this.tipojuego = tipo_juego;
        this.fijo = fijo;
        this.corrido = corrido;
        this.parlet = parlet;
    }

    public int getTipo_juego() {
        return tipojuego;
    }

    public void setTipo_juego(int tipo_juego) {
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
}
