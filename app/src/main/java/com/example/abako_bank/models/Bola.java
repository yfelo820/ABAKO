package com.example.abako_bank.models;

public class Bola {

    private int _id;
    private String numero;
    private boolean envio;
    private String fecha;
    private int monto_corrido;
    private int monto_fijo;
    private int monto_total;
    private boolean ganador;
    private double premio;

    public Bola() {
    }

    public Bola(int _id, String numero, boolean envio, String fecha, int monto_corrido, int monto_fijo, int monto_total, boolean ganador, double premio) {
        this._id = _id;
        this.numero = numero;
        this.envio = envio;
        this.fecha = fecha;
        this.monto_corrido = monto_corrido;
        this.monto_fijo = monto_fijo;
        this.monto_total = monto_total;
        this.ganador = ganador;
        this.premio = premio;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public boolean isEnvio() {
        return envio;
    }

    public void setEnvio(boolean envio) {
        this.envio = envio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getMonto_corrido() {
        return monto_corrido;
    }

    public void setMonto_corrido(int monto_corrido) {
        this.monto_corrido = monto_corrido;
    }

    public int getMonto_fijo() {
        return monto_fijo;
    }

    public void setMonto_fijo(int monto_fijo) {
        this.monto_fijo = monto_fijo;
    }

    public int getMonto_total() {
        return monto_total;
    }

    public void setMonto_total(int monto_total) {
        this.monto_total = monto_total;
    }

    public boolean isGanador() {
        return ganador;
    }

    public void setGanador(boolean ganador) {
        this.ganador = ganador;
    }

    public double getPremio() {
        return premio;
    }

    public void setPremio(double premio) {
        this.premio = premio;
    }
}
