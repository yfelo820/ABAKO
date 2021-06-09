package com.example.abako_bank.models;

import java.util.List;

public class Jugada {

    private int _id;
    private int tipo_jugada;
    private int tipo_tirada;
    private boolean estado;
    private String fecha;
    private double bruto;
    private double limpio;
    private double pago_listero;
    private double ganancia_banco;
    private double premio;
    private Tiro tiro;
    List<Bola> bolas;
    List<Centena> centenas;
    List<Parlet> parlets;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getTipo_jugada() {
        return tipo_jugada;
    }

    public void setTipo_jugada(int tipo_jugada) {
        this.tipo_jugada = tipo_jugada;
    }

    public int getTipo_tirada() {
        return tipo_tirada;
    }

    public void setTipo_tirada(int tipo_tirada) {
        this.tipo_tirada = tipo_tirada;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getBruto() {
        return bruto;
    }

    public void setBruto(double bruto) {
        this.bruto = bruto;
    }

    public double getLimpio() {
        return limpio;
    }

    public void setLimpio(double limpio) {
        this.limpio = limpio;
    }

    public double getPago_listero() {
        return pago_listero;
    }

    public void setPago_listero(double pago_listero) {
        this.pago_listero = pago_listero;
    }

    public double getGanancia_banco() {
        return ganancia_banco;
    }

    public void setGanancia_banco(double ganancia_banco) {
        this.ganancia_banco = ganancia_banco;
    }

    public double getPremio() {
        return premio;
    }

    public void setPremio(double premio) {
        this.premio = premio;
    }

    public Tiro getTiro() {
        return tiro;
    }

    public void setTiro(Tiro tiro) {
        this.tiro = tiro;
    }

    public List<Bola> getBolas() {
        return bolas;
    }

    public void setBolas(List<Bola> bolas) {
        this.bolas = bolas;
    }

    public List<Centena> getCentenas() {
        return centenas;
    }

    public void setCentenas(List<Centena> centenas) {
        this.centenas = centenas;
    }

    public List<Parlet> getParlets() {
        return parlets;
    }

    public void setParlets(List<Parlet> parlets) {
        this.parlets = parlets;
    }
}
