package com.example.abako_bank.models.config;

import com.orm.SugarRecord;

public class Config_Percent_Pago extends SugarRecord<Config_Percent_Pago> {

    private int tipojuego;//1-bote 2-charada

    private double bola_normal;

    private double parlet_normal;

    private double centena_normal;

    private double bola_no_normal;

    private double parlet_no_normal;

    private double centene_no_normal;

    public Config_Percent_Pago() {
    }

    public Config_Percent_Pago(double bola_normal, double parlet_normal, double centena_normal,
                               double bola_no_normal, double parlet_no_normal, double centene_no_normal,
                               int tipo_juego) {
        this.bola_normal = bola_normal;
        this.parlet_normal = parlet_normal;
        this.centena_normal = centena_normal;
        this.bola_no_normal = bola_no_normal;
        this.parlet_no_normal = parlet_no_normal;
        this.centene_no_normal = centene_no_normal;
        this.tipojuego = tipo_juego;
    }

    public double getBola_normal() {
        return bola_normal;
    }

    public void setBola_normal(double bola_normal) {
        this.bola_normal = bola_normal;
    }

    public double getParlet_normal() {
        return parlet_normal;
    }

    public void setParlet_normal(double parlet_normal) {
        this.parlet_normal = parlet_normal;
    }

    public double getCentena_normal() {
        return centena_normal;
    }

    public void setCentena_normal(double centena_normal) {
        this.centena_normal = centena_normal;
    }

    public double getBola_no_normal() {
        return bola_no_normal;
    }

    public void setBola_no_normal(double bola_no_normal) {
        this.bola_no_normal = bola_no_normal;
    }

    public double getParlet_no_normal() {
        return parlet_no_normal;
    }

    public void setParlet_no_normal(double parlet_no_normal) {
        this.parlet_no_normal = parlet_no_normal;
    }

    public double getCentene_no_normal() {
        return centene_no_normal;
    }

    public void setCentene_no_normal(double centene_no_normal) {
        this.centene_no_normal = centene_no_normal;
    }

    public int getTipo_juego() {
        return tipojuego;
    }

    public void setTipo_juego(int tipo_juego) {
        this.tipojuego = tipo_juego;
    }

}
